package team05a.secondhand.address.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import team05a.secondhand.IntegrationTestSupport;
import team05a.secondhand.address.data.dto.AddressListResponse;

class AddressServiceTest extends IntegrationTestSupport {

	@Autowired
	private AddressService addressService;

	@DisplayName("동네 목록을 조회한다.")
	@Test
	void findAll() {
		// given & when
		int page = 0;
		int size = 10;
		Pageable pageable = PageRequest.of(page, size);
		AddressListResponse response = addressService.findAll(pageable);

		// then
		assertThat(response.getAddresses().get(0).getId()).isEqualTo(1);
		assertThat(response.isHasNext()).isTrue();
	}
}
