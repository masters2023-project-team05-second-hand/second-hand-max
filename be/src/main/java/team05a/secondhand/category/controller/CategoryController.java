package team05a.secondhand.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team05a.secondhand.category.data.dto.CategoryResponse;
import team05a.secondhand.category.service.CategoryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryResponse>> retrieveList() {
        return ResponseEntity.ok()
            .body(categoryService.findAll());
    }
}
