package team05a.secondhand.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team05a.secondhand.category.data.dto.CategoriesResponse;
import team05a.secondhand.category.repository.CategoryRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoriesResponse findAll() {
        return CategoriesResponse.from(categoryRepository.findAll());
    }
}
