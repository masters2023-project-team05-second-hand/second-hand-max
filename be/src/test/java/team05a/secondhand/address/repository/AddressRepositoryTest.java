package team05a.secondhand.address.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import team05a.secondhand.IntegrationTestSupport;
import team05a.secondhand.address.data.entity.Address;

class AddressRepositoryTest extends IntegrationTestSupport {

	@Autowired
	private AddressRepository addressRepository;

	@DisplayName("동네 목록을 조회한다.")
	@Test
	void findAll() {
		// given & when
		int page = 0;
		int size = 10;
		Pageable pageable = PageRequest.of(page, size);
		Page<Address> response = addressRepository.findAll(pageable);

		// then
		assertThat(response.toList().get(0).getId()).isEqualTo(1);
		assertThat(response.hasNext()).isTrue();
	}
}