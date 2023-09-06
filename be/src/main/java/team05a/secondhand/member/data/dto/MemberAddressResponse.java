package team05a.secondhand.member.data.dto;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.member_address.data.entity.MemberAddress;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberAddressResponse {

    private final Long id;
    private final String name;
    private final boolean isLastVisited;

    @Builder
    private MemberAddressResponse(Long id, String name, boolean isLastVisited) {
        this.id = id;
        this.name = name;
        this.isLastVisited = isLastVisited;
    }

    public static List<MemberAddressResponse> from(List<MemberAddress> memberAddresses) {
        return memberAddresses.stream()
                .map(MemberAddressResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static MemberAddressResponse from(MemberAddress memberAddress) {
        return MemberAddressResponse.builder()
                .id(memberAddress.getAddress().getId())
                .name(memberAddress.getAddress().getName())
                .isLastVisited(memberAddress.isLastVisited())
                .build();
    }
}
