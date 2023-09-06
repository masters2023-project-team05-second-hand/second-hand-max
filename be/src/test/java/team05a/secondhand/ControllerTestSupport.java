package team05a.secondhand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import team05a.secondhand.address.controller.AddressController;
import team05a.secondhand.address.service.AddressService;
import team05a.secondhand.category.controller.CategoryController;
import team05a.secondhand.category.service.CategoryService;
import team05a.secondhand.image.service.ImageService;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.resolver.LoginArgumentResolver;
import team05a.secondhand.product.controller.ProductController;
import team05a.secondhand.product.service.ProductService;

@WebMvcTest(controllers = {
	AddressController.class,
	CategoryController.class,
	ProductController.class
})
@Import(value = {JwtTokenProvider.class})
public abstract class ControllerTestSupport {

	@Autowired
	protected MockMvc mockMvc;
	@MockBean
	protected ProductService productService;
	@MockBean
	protected ImageService imageService;
	@MockBean
	protected AddressService addressService;
	@MockBean
	protected LoginArgumentResolver loginArgumentResolver;
	@MockBean
	protected CategoryService categoryService;
}
