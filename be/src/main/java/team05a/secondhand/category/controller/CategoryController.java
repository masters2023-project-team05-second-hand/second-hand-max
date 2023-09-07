package team05a.secondhand.category.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.category.data.dto.CategoriesResponse;
import team05a.secondhand.category.data.dto.CategoryResponse;
import team05a.secondhand.category.service.CategoryService;

@RequiredArgsConstructor
@RestController
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping("/api/categories")
	public ResponseEntity<CategoriesResponse> retrieveList() {
		return ResponseEntity.ok()
			.body(categoryService.findAll());
	}
}
