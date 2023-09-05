package team05a.secondhand.member_address;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.address.repository.AddressRepository;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.member_address.data.entity.MemberAddress;
import team05a.secondhand.member_address.repository.MemberAddressRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberAddressRepositoryTest {

    @Autowired
    private MemberAddressRepository memberAddressRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("멤버 아이디로 Member Address를 조회할 수 있다.")
    void findByMemberId() {
        //given & when
        Optional<Member> member = memberRepository.findById(1L);
        Optional<Address> address1 = addressRepository.findById(1L);
        Optional<Address> address2 = addressRepository.findById(2L);
        List<MemberAddress> memberAddresses = memberAddressRepository.findByMemberId(1L);

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(memberAddresses).hasSize(2);
            assertions.assertThat(memberAddresses.get(1).isLastVisited()).isFalse();
        });
    }

    @Test
    @DisplayName("멤버 아이디에 해당하는 모든 MemberAddress를 삭제한다.")
    void deleteByMemberId() {
        //given & when
        memberAddressRepository.deleteByMemberId(1L);

        //then
        assertThat(memberAddressRepository.findByMemberId(1L)).isEmpty();
    }
}
