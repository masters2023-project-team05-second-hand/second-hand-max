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
import team05a.secondhand.errors.exception.StatusNotFoundException;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.dto.ProductCreateRequest;
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

	public Product create(ProductCreateRequest productCreateRequest, Long memberId,
		List<String> imageUrls) {
		Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
		Category category = categoryRepository.findById(productCreateRequest.getCategoryId())
			.orElseThrow(CategoryNotFoundException::new);
		Address address = addressRepository.findById(productCreateRequest.getAddressId())
			.orElseThrow(AddressNotFoundException::new);
		Status status = statusRepository.findById(1L).orElseThrow(StatusNotFoundException::new);
		return productRepository.save(
			productCreateRequest.toEntity(member, category, address, status, imageUrls.get(0)));
	}
}
