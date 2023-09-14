package team05a.secondhand.member.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.address.repository.AddressRepository;
import team05a.secondhand.errors.exception.MemberNotFoundException;
import team05a.secondhand.image.service.ImageService;
import team05a.secondhand.member.data.dto.MemberAddressResponse;
import team05a.secondhand.member.data.dto.MemberAddressUpdateRequest;
import team05a.secondhand.member.data.dto.MemberResponse;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.member_address.data.entity.MemberAddress;
import team05a.secondhand.member_address.repository.MemberAddressRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final ImageService imageService;
	private final MemberRepository memberRepository;
	private final AddressRepository addressRepository;
	private final MemberAddressRepository memberAddressRepository;

	public MemberResponse getMember(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

		return MemberResponse.from(member);
	}

	@Transactional
	public List<MemberAddressResponse> updateMemberAddresses(Long memberId,
		MemberAddressUpdateRequest memberAddressUpdateRequest) {
		memberAddressRepository.deleteByMemberId(memberId);
		Member member = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);
		List<Address> addresses = memberAddressUpdateRequest.getAddressIds().stream()
			.map(id -> addressRepository.findById(id).orElseThrow(RuntimeException::new))
			.collect(Collectors.toList());
		List<MemberAddress> memberAddresses = memberAddressRepository.saveAll(MemberAddress.of(member, addresses));

		return MemberAddressResponse.from(memberAddresses);
	}

	@Transactional(readOnly = true)
	public List<MemberAddressResponse> getMemberAddress(Long memberId) {
		List<MemberAddress> memberAddresses = memberAddressRepository.findByMemberId(memberId);

		return MemberAddressResponse.from(memberAddresses);
	}

	@Transactional
	public String updateMemberProfile(Long memberId, MultipartFile newProfileImg) {
		String newProfileImgUrl = imageService.uploadImage(newProfileImg);
		Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

		return member.updateProfileImgUrl(newProfileImgUrl);
	}
}
