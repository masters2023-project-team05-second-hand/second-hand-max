package team05a.secondhand.member.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 315580793L;

    public static final QMember member = new QMember("member1");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<team05a.secondhand.member_address.data.entity.MemberAddress, team05a.secondhand.member_address.data.entity.QMemberAddress> memberAddresses = this.<team05a.secondhand.member_address.data.entity.MemberAddress, team05a.secondhand.member_address.data.entity.QMemberAddress>createList("memberAddresses", team05a.secondhand.member_address.data.entity.MemberAddress.class, team05a.secondhand.member_address.data.entity.QMemberAddress.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final StringPath profileImgUrl = createString("profileImgUrl");

    public final EnumPath<team05a.secondhand.oauth.OauthAttributes> type = createEnum("type", team05a.secondhand.oauth.OauthAttributes.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

