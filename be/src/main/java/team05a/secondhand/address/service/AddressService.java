package team05a.secondhand.address.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.address.data.dto.AddressListResponse;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.address.repository.AddressRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class AddressService {

	private final AddressRepository addressRepository;

	public AddressListResponse findAll(Pageable pageable) {
		Slice<Address> addresses = addressRepository.findAll(pageable);
		return AddressListResponse.from(addresses);
	}
}
