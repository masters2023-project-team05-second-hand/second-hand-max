package team05a.secondhand.product.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.image.service.ImageService;
import team05a.secondhand.member.resolver.MemberId;
import team05a.secondhand.product.data.dto.ProductCreateRequest;
import team05a.secondhand.product.data.dto.ProductIdResponse;
import team05a.secondhand.product.data.dto.ProductResponse;
import team05a.secondhand.product.data.dto.ProductStatusResponse;
import team05a.secondhand.product.data.dto.ProductUpdateRequest;
import team05a.secondhand.product.data.dto.ProductUpdateStatusRequest;
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
	public ResponseEntity<ProductResponse> read(@PathVariable Long productId) {
		ProductResponse productResponse = productService.read(productId);

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

	@PatchMapping("/{productId}/status")
	public ResponseEntity<ProductStatusResponse> updateStatus(
		@RequestBody ProductUpdateStatusRequest productUpdateStatusRequest,
		@PathVariable Long productId, @MemberId Long memberId) {
		ProductStatusResponse productStatusResponse = productService.updateStatus(productUpdateStatusRequest, productId,
			memberId);
		return ResponseEntity.ok()
			.body(productStatusResponse);
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<ProductIdResponse> delete(@PathVariable Long productId, @MemberId Long memberId) {
		ProductIdResponse productIdResponse = productService.delete(productId, memberId);
		return ResponseEntity.ok()
			.body(productIdResponse);
	}
}
