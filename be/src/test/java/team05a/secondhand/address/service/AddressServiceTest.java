package team05a.secondhand.address.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import team05a.secondhand.address.data.dto.AddressListResponse;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.address.repository.AddressRepository;
import team05a.secondhand.fixture.FixtureFactory;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

	@InjectMocks
	private AddressService addressService;
	@Mock
	private AddressRepository addressRepository;

	@DisplayName("동네 목록을 조회한다.")
	@Test
	void findAll() {
		// given
		int page = 0;
		int size = 10;
		Pageable pageable = PageRequest.of(page, size);
		Address address = FixtureFactory.createAddress();
		Page<Address> addressPage = new PageImpl<>(List.of(address), pageable, 50L);
		given(addressRepository.findAll(pageable)).willReturn(addressPage);

		// when
		AddressListResponse response = addressService.findAll(pageable);

		// then
		assertThat(response.getAddresses().get(0).getId()).isEqualTo(address.getId());
		assertThat(response.getAddresses().get(0).getName()).isEqualTo(address.getName());
		assertThat(response.isHasNext()).isTrue();
	}
}
