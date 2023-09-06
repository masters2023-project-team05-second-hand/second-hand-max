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
import team05a.secondhand.errors.exception.StatusNotFoundException;
import team05a.secondhand.errors.exception.UnauthorizedProductModificationException;
import team05a.secondhand.image.service.ImageService;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.dto.ProductCreateRequest;
import team05a.secondhand.product.data.dto.ProductIdResponse;
import team05a.secondhand.product.data.dto.ProductUpdateRequest;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;
import team05a.secondhand.status.data.entity.Status;
import team05a.secondhand.status.repository.StatusRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;
	private final CategoryRepository categoryRepository;
	private final AddressRepository addressRepository;
	private final StatusRepository statusRepository;
	private final ImageService imageService;

	public ProductIdResponse create(ProductCreateRequest productCreateRequest, Long memberId) {
		List<String> imageUrls = imageService.upload(productCreateRequest.getImages());
		Product product = createProduct(productCreateRequest, memberId, imageUrls);
		imageService.create(product, imageUrls);
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

	public ProductIdResponse update(ProductUpdateRequest productUpdateRequest, Long productId, Long memberId) {
		validateProductSeller(productId, memberId);
		imageService.deleteAllBy(productUpdateRequest.getDeletedImageIds());
		Long imageCount = imageService.count(productId);
		List<String> newImageUrls = imageService.uploadNew(imageCount, productUpdateRequest.getNewImages());
		String thumbnailUrl = getThumbnailUrl(imageCount, newImageUrls);
		Product updateProduct = updateProduct(productUpdateRequest, productId, thumbnailUrl);
		imageService.create(updateProduct, newImageUrls);
		return ProductIdResponse.from(updateProduct.getId());
	}

	private String getThumbnailUrl(Long imageCount, List<String> newImageUrls) {
		if (imageCount == 0) {
			return newImageUrls.get(0);
		}
		return null;
	}

	private Product updateProduct(ProductUpdateRequest productUpdateRequest, Long productId, String thumbnailUrl) {
		Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
		Category category = categoryRepository.findById(productUpdateRequest.getCategoryId())
			.orElseThrow(CategoryNotFoundException::new);
		Address address = addressRepository.findById(productUpdateRequest.getAddressId())
			.orElseThrow(AddressNotFoundException::new);
		thumbnailUrl = (thumbnailUrl == null) ? imageService.findThumbnail(product) : thumbnailUrl;
		return product.modify(productUpdateRequest, category, address, thumbnailUrl);
	}

	private void validateProductSeller(Long productId, Long memberId) {
		existProduct(productId);
		Member member = existMember(memberId);
		if (!productRepository.existsByIdAndMember(productId, member)) {
			throw new UnauthorizedProductModificationException();
		}
	}

	private Member existMember(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
	}

	private void existProduct(Long productId) {
		if (!productRepository.existsById(productId)) {
			throw new ProductNotFoundException();
		}
	}
}
