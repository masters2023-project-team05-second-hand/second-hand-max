package team05a.secondhand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import team05a.secondhand.address.controller.AddressController;
import team05a.secondhand.address.service.AddressService;

@WebMvcTest(controllers = {
	AddressController.class
})
public abstract class ControllerTestSupport {

	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected ObjectMapper objectMapper;
	@MockBean
	protected AddressService addressService;
}
