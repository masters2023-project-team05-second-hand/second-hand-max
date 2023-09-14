package team05a.secondhand.product.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1479021005L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final team05a.secondhand.address.data.entity.QAddress address;

    public final team05a.secondhand.category.data.entity.QCategory category;

    public final StringPath content = createString("content");

    public final DateTimePath<java.sql.Timestamp> createdTime = createDateTime("createdTime", java.sql.Timestamp.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final team05a.secondhand.member.data.entity.QMember member;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final ListPath<team05a.secondhand.image.data.entity.ProductImage, team05a.secondhand.image.data.entity.QProductImage> productImages = this.<team05a.secondhand.image.data.entity.ProductImage, team05a.secondhand.image.data.entity.QProductImage>createList("productImages", team05a.secondhand.image.data.entity.ProductImage.class, team05a.secondhand.image.data.entity.QProductImage.class, PathInits.DIRECT2);

    public final team05a.secondhand.status.data.entity.QStatus status;

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new team05a.secondhand.address.data.entity.QAddress(forProperty("address")) : null;
        this.category = inits.isInitialized("category") ? new team05a.secondhand.category.data.entity.QCategory(forProperty("category")) : null;
        this.member = inits.isInitialized("member") ? new team05a.secondhand.member.data.entity.QMember(forProperty("member")) : null;
        this.status = inits.isInitialized("status") ? new team05a.secondhand.status.data.entity.QStatus(forProperty("status")) : null;
    }

}

