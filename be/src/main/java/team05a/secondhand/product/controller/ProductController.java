package team05a.secondhand.product.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.image.service.ImageService;
import team05a.secondhand.member.resolver.MemberId;
import team05a.secondhand.product.data.dto.ProductCreateRequest;
import team05a.secondhand.product.data.dto.ProductIdResponse;
import team05a.secondhand.product.data.dto.ProductResponse;
import team05a.secondhand.product.data.dto.ProductUpdateRequest;
import team05a.secondhand.product.service.ProductService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;
	private final ImageService imageService;

	@PostMapping
	public ResponseEntity<ProductIdResponse> create(
		@ModelAttribute @Valid ProductCreateRequest productCreateRequest, @MemberId Long memberId) {
		ProductIdResponse productIdResponse = productService.create(productCreateRequest, memberId);
		return ResponseEntity.ok()
			.body(productIdResponse);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponse> read(@PathVariable Long productId, @MemberId Long memberId) {
		ProductResponse productResponse = productService.read(productId, memberId);

		return ResponseEntity.ok()
			.body(productResponse);
	}

	@PatchMapping("/{productId}")
	public ResponseEntity<ProductIdResponse> update(@ModelAttribute @Valid ProductUpdateRequest productUpdateRequest,
		@PathVariable Long productId, @MemberId Long memberId) {
		ProductIdResponse productIdResponse = productService.update(productUpdateRequest, productId, memberId);
		return ResponseEntity.ok()
			.body(productIdResponse);
	}
}
