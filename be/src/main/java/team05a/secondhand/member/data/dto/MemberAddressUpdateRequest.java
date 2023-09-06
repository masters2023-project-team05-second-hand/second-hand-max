package team05a.secondhand.member.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAddressUpdateRequest {

    private List<Long> addressIds;

    @Builder
    private MemberAddressUpdateRequest(List<Long> addressIds) {
        this.addressIds = addressIds;
    }
}
