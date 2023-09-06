package team05a.secondhand.category.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long id;
	@Column(length = 10, nullable = false)
	private String name;
	@Column(length = 50)
	private String ImgUrl;

	@Builder
	public Category(Long id, String name, String imgUrl) {
		this.id = id;
		this.name = name;
		ImgUrl = imgUrl;
	}
}
