package team05a.secondhand.product.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.image.service.ImageService;
import team05a.secondhand.member.resolver.MemberId;
import team05a.secondhand.product.data.dto.ProductCreateRequest;
import team05a.secondhand.product.data.dto.ProductCreateResponse;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.service.ProductService;

@RequiredArgsConstructor
@RestController
public class ProductController {

	private final ProductService productService;
	private final ImageService imageService;

	@PostMapping("/api/products")
	public ResponseEntity<ProductCreateResponse> create(
		@ModelAttribute @Valid ProductCreateRequest productCreateRequest, @MemberId Long memberId) {
		List<String> imageUrls = imageService.upload(productCreateRequest.getImages());
		Product product = productService.create(productCreateRequest, memberId, imageUrls);
		imageService.create(product, imageUrls);
		return ResponseEntity.ok()
			.body(ProductCreateResponse.from(product.getId()));
	}
}
