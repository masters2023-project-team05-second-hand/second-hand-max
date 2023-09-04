package team05a.secondhand.address.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.address.data.dto.AddressListResponse;
import team05a.secondhand.address.service.AddressService;

@RequiredArgsConstructor
@RestController
public class AddressController {

	private final AddressService addressService;

	@GetMapping("/api/addresses")
	public ResponseEntity<AddressListResponse> retrieveList(Pageable pageable) {
		AddressListResponse addressListResponse = addressService.findAll(pageable);
		return ResponseEntity.ok()
			.body(addressListResponse);
	}
}
