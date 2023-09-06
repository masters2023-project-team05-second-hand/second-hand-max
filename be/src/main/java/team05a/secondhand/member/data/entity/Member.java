package team05a.secondhand.member.data.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.member_address.data.entity.MemberAddress;
import team05a.secondhand.oauth.OauthAttributes;
import team05a.secondhand.oauth.data.dto.MemberOauthRequest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "email_type_unique",
                columnNames = {"email", "type"}
        )})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private OauthAttributes type;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(length = 50, nullable = false)
    private String nickname;
    @Column(length = 100)
    private String profileImgUrl;
    @OneToMany(mappedBy = "member")
    private List<MemberAddress> memberAddresses = new ArrayList<>();

    @Builder
    private Member(OauthAttributes type, String email, String nickname, String profileImgUrl) {
        this.type = type;
        this.email = email;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    public static Member from(MemberOauthRequest memberOauthRequest) {
        return new Member(memberOauthRequest.getType(), memberOauthRequest.getEmail(), memberOauthRequest.getNickname(),
                memberOauthRequest.getProfileImgUrl());
    }
}
