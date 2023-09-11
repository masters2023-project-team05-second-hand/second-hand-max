package team05a.secondhand.product.data.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.category.data.entity.Category;
import team05a.secondhand.image.data.entity.ProductImage;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.product.data.dto.ProductUpdateRequest;
import team05a.secondhand.status.data.entity.Status;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id")
	private Member member;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Address address;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Status status;
	@Column(length = 50, nullable = false)
	private String title;
	@Lob
	private String content;
	private Integer price;
	@CreationTimestamp
	private Timestamp createdTime;
	@Column(length = 200, nullable = false)
	private String thumbnailUrl;
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductImage> productImages = new ArrayList<>();

	@Builder
	private Product(Long id, Member member, Category category, Address address, Status status, String title,
		String content, Integer price, Timestamp createdTime, String thumbnailUrl, List<ProductImage> productImages) {
		this.id = id;
		this.member = member;
		this.category = category;
		this.address = address;
		this.status = status;
		this.title = title;
		this.content = content;
		this.price = price;
		this.createdTime = createdTime;
		this.thumbnailUrl = thumbnailUrl;
		this.productImages = productImages;
	}

	public Product modify(ProductUpdateRequest productUpdateRequest, Category category, Address address,
		String thumbnailUrl) {
		this.category = category;
		this.address = address;
		this.title = productUpdateRequest.getTitle();
		this.content = productUpdateRequest.getContent();
		this.price = productUpdateRequest.getPrice();
		this.thumbnailUrl = thumbnailUrl;
		return this;
	}

	public void modifyStatus(Status status) {
		this.status = status;
	}
}
