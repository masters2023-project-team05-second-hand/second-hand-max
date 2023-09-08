package team05a.secondhand.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team05a.secondhand.category.data.dto.CategoryResponse;
import team05a.secondhand.category.repository.CategoryRepository;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> findAll() {
        return CategoryResponse.from(categoryRepository.findAll());
    }
}
