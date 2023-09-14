package team05a.secondhand.redis.repository;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import team05a.secondhand.AcceptanceTest;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.dto.ProductCreateRequest;
import team05a.secondhand.product.data.dto.ProductIdResponse;
import team05a.secondhand.product.service.ProductService;

class RedisRepositoryTest extends AcceptanceTest {

	@Autowired
	private RedisRepository redisRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("기간이 있도록 데이터를 저장한다.")
	@Test
	void setTime() {
		//given & when
		String key = "test:abc";
		Long value = 1L;
		long time = 10000L;
		redisRepository.setTime(key, value, time);
		Long save = (Long)redisRepository.get(key).orElseThrow();

		//then
		assertThat(save).isEqualTo(value);
	}

	@DisplayName("기간이 없도록 데이터를 저장한다.")
	@Test
	void set() {
		//given & when
		String key = "test:abc";
		Long value = 1L;
		redisRepository.set(key, value);
		Long save = (Long)redisRepository.get(key).orElseThrow();

		//then
		assertThat(save).isEqualTo(value);
	}

	@DisplayName("키 값으로 데이터를 삭제한다.")
	@Test
	void delete() {
		//given & when
		String key = "test:abc";
		Long value = 1L;
		redisRepository.set(key, value);
		Boolean delete = redisRepository.delete(key);

		//then
		assertThat(delete).isTrue();
	}

	@DisplayName("상품을 등록 후 조회수가 0인지 확인한다.")
	@Test
	void createProduct_getView() throws IOException {
		//given & when
		Member member = memberRepository.save(FixtureFactory.createMember());
		ProductCreateRequest productCreateRequest = FixtureFactory.productCreateRequestWithMultipartFile();
		ProductIdResponse productIdResponse = productService.create(productCreateRequest, member.getId());
		String key = "view:" + productIdResponse.getProductId();
		Long view = (Long)redisRepository.get(key).orElseThrow();

		//then
		assertThat(view).isEqualTo(0);
	}

	@DisplayName("상품을 등록 후 조회하면 조회수가 1인지 확인한다.")
	@Test
	void readProduct_getView() throws IOException {
		//given & when
		Member member = memberRepository.save(FixtureFactory.createMember());
		ProductCreateRequest productCreateRequest = FixtureFactory.productCreateRequestWithMultipartFile();
		ProductIdResponse productIdResponse = productService.create(productCreateRequest, member.getId());
		productService.read(productIdResponse.getProductId());
		String key = "view:" + productIdResponse.getProductId();
		Long view = (Long)redisRepository.get(key).orElseThrow();

		//then
		assertThat(view).isEqualTo(1);
	}
}
