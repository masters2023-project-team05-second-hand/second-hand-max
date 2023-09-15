package team05a.secondhand.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.address.repository.AddressRepository;
import team05a.secondhand.category.data.entity.Category;
import team05a.secondhand.category.repository.CategoryRepository;
import team05a.secondhand.errors.exception.AddressNotFoundException;
import team05a.secondhand.errors.exception.CategoryNotFoundException;
import team05a.secondhand.errors.exception.MemberNotFoundException;
import team05a.secondhand.errors.exception.ProductNotFoundException;
import team05a.secondhand.errors.exception.ProductViewNotFoundException;
import team05a.secondhand.errors.exception.StatusNotFoundException;
import team05a.secondhand.errors.exception.UnauthorizedProductModificationException;
import team05a.secondhand.image.data.entity.ProductImage;
import team05a.secondhand.image.service.ImageService;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.dto.ProductCreateRequest;
import team05a.secondhand.product.data.dto.ProductIdResponse;
import team05a.secondhand.product.data.dto.ProductListResponse;
import team05a.secondhand.product.data.dto.ProductResponse;
import team05a.secondhand.product.data.dto.ProductStatusResponse;
import team05a.secondhand.product.data.dto.ProductUpdateRequest;
import team05a.secondhand.product.data.dto.ProductUpdateStatusRequest;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;
import team05a.secondhand.redis.repository.RedisRepository;
import team05a.secondhand.status.data.entity.Status;
import team05a.secondhand.status.repository.StatusRepository;

@RequiredArgsConstructor
@Service
public class ProductService {

	public static final String VIEW_PREFIX = "view:";

	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;
	private final CategoryRepository categoryRepository;
	private final AddressRepository addressRepository;
	private final StatusRepository statusRepository;
	private final ImageService imageService;
	private final RedisRepository redisRepository;

	@Transactional
	public ProductIdResponse create(ProductCreateRequest productCreateRequest, Long memberId) {
		List<String> imageUrls = imageService.uploadProductImages(productCreateRequest.getImages());
		Product product = createProduct(productCreateRequest, memberId, imageUrls);
		imageService.create(product, imageUrls);
		redisRepository.set(VIEW_PREFIX + product.getId(), 0L);
		return ProductIdResponse.from(product.getId());
	}

	private Product createProduct(ProductCreateRequest productCreateRequest, Long memberId, List<String> imageUrls) {
		Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
		Category category = categoryRepository.findById(productCreateRequest.getCategoryId())
			.orElseThrow(CategoryNotFoundException::new);
		Address address = addressRepository.findById(productCreateRequest.getAddressId())
			.orElseThrow(AddressNotFoundException::new);
		Status status = statusRepository.findById(1L).orElseThrow(StatusNotFoundException::new);
		return productRepository.save(
			productCreateRequest.toEntity(member, category, address, status, imageUrls.get(0)));
	}

	@Transactional(readOnly = true)
	public ProductResponse read(Long productId) {
		Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
		List<ProductImage> productImages = imageService.findAllByProduct(product);
		String key = VIEW_PREFIX + productId;
		Long view = (Long)redisRepository.get(key).orElseThrow(ProductViewNotFoundException::new) + 1;
		redisRepository.set(key, view);

		return ProductResponse.from(product, productImages);
	}

	@Transactional
	public ProductIdResponse update(ProductUpdateRequest productUpdateRequest, Long productId, Long memberId) {
		validateProductSeller(productId, memberId);
		imageService.deleteAllBy(productUpdateRequest.getDeletedImageIds());
		int imageCount = imageService.countImagesBy(productId);
		List<String> newImageUrls = imageService.uploadNew(imageCount, productUpdateRequest.getNewImages());
		String thumbnailUrl = getThumbnailUrl(imageCount, newImageUrls, productId);
		Product updateProduct = updateProduct(productUpdateRequest, productId, thumbnailUrl);
		imageService.create(updateProduct, newImageUrls);
		return ProductIdResponse.from(updateProduct.getId());
	}

	private String getThumbnailUrl(int imageCount, List<String> newImageUrls, Long productId) {
		if (imageCount == 0) {
			return newImageUrls.get(0);
		}
		return imageService.findThumbnail(productId);
	}

	private Product updateProduct(ProductUpdateRequest productUpdateRequest, Long productId, String thumbnailUrl) {
		Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
		Category category = categoryRepository.findById(productUpdateRequest.getCategoryId())
			.orElseThrow(CategoryNotFoundException::new);
		Address address = addressRepository.findById(productUpdateRequest.getAddressId())
			.orElseThrow(AddressNotFoundException::new);
		return product.modify(productUpdateRequest, category, address, thumbnailUrl);
	}

	private void validateProductSeller(Long productId, Long memberId) {
		checkProduct(productId);
		checkMember(memberId);
		if (!productRepository.existsByIdAndMemberId(productId, memberId)) {
			throw new UnauthorizedProductModificationException();
		}
	}

	private void checkMember(Long memberId) {
		if (!memberRepository.existsById(memberId)) {
			throw new MemberNotFoundException();
		}
	}

	private void checkProduct(Long productId) {
		if (!productRepository.existsById(productId)) {
			throw new ProductNotFoundException();
		}
	}

	@Transactional
	public ProductIdResponse delete(Long productId, Long memberId) {
		validateProductSeller(productId, memberId);
		productRepository.deleteById(productId);
		return ProductIdResponse.from(productId);
	}

	@Transactional
	public ProductStatusResponse updateStatus(ProductUpdateStatusRequest productUpdateStatusRequest, Long productId,
		Long memberId) {
		validateProductSeller(productId, memberId);
		Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
		Status status = statusRepository.findById(productUpdateStatusRequest.getStatusId())
			.orElseThrow(StatusNotFoundException::new);
		product.modifyStatus(status);
		return ProductStatusResponse.from(productUpdateStatusRequest.getStatusId());
	}

	@Transactional(readOnly = true)
	public ProductListResponse readList(Long addressId, Long categoryId, Long cursor, Long size) {
		return productRepository.findList(addressId, categoryId, cursor, size);
	}
}
