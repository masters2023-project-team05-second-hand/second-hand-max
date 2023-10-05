package team05a.secondhand.chat.integration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import team05a.secondhand.AcceptanceTest;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.address.repository.AddressRepository;
import team05a.secondhand.category.data.entity.Category;
import team05a.secondhand.category.repository.CategoryRepository;
import team05a.secondhand.chat.data.dto.ChatRoomListResponse;
import team05a.secondhand.chat.data.dto.MessageReadResponse;
import team05a.secondhand.chat.data.entity.ChatRoom;
import team05a.secondhand.chat.data.entity.Message;
import team05a.secondhand.chat.repository.ChatRoomRepository;
import team05a.secondhand.chat.repository.MessageRepository;
import team05a.secondhand.errors.exception.AddressNotFoundException;
import team05a.secondhand.errors.exception.CategoryNotFoundException;
import team05a.secondhand.errors.exception.StatusNotFoundException;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.image.service.ImageService;
import team05a.secondhand.jwt.JwtTokenProvider;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.dto.ProductCreateRequest;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;
import team05a.secondhand.status.data.entity.Status;
import team05a.secondhand.status.repository.StatusRepository;

public class ChatRestAssuredTest extends AcceptanceTest {

	@Autowired
	private ChatRoomRepository chatRoomRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private ImageService imageService;
	@Autowired
	private MessageRepository messageRepository;

	@DisplayName("채팅방 목록을 조회한다.")
	@Test
	void readList_success() throws IOException, InterruptedException {
		// given
		Member member = singUp();
		Member otherMember = signupAnotherMember();
		Product product = createProduct(member);
		ChatRoom chatRoom = FixtureFactory.createChatRoom(otherMember, product);
		ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
		Message message = FixtureFactory.createMessage(savedChatRoom.getUuid(), otherMember, "얼마에요?");
		messageRepository.save(message);
		Message message1 = FixtureFactory.createMessage(savedChatRoom.getUuid(), member, "얼마 원하세요??");
		messageRepository.save(message1);
		Message message2 = FixtureFactory.createMessage(savedChatRoom.getUuid(), member, "나눔해주세용");
		messageRepository.save(message2);
		Thread.sleep(1000);
		Product product1 = createProduct(member);
		ChatRoom chatRoom1 = FixtureFactory.createChatRoom(otherMember, product1);
		ChatRoom savedChatRoom1 = chatRoomRepository.save(chatRoom1);
		Message otherMessage = FixtureFactory.createMessage(savedChatRoom1.getUuid(), otherMember, "얼마?");
		messageRepository.save(otherMessage);

		// when
		var response = readList(otherMember.getId());

		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
			softAssertions.assertThat(response.jsonPath()
				.getList("", ChatRoomListResponse.class)
				.get(0)
				.getOtherMember()
				.getId()).isEqualTo(product.getMember().getId());
		});
	}

	private ExtractableResponse<Response> readList(Long memberId) {
		return RestAssured
			.given().log().all()
			.header(HttpHeaders.AUTHORIZATION,
				"Bearer " + jwtTokenProvider.createAccessToken(Map.of("memberId", memberId)))
			.when()
			.get("/api/chat-room")
			.then().log().all()
			.extract();
	}

	@DisplayName("채팅 메세지를 조회한다.")
	@Test
	void readMessages_success() throws IOException, InterruptedException {
		// given
		Member member = singUp();
		Member otherMember = signupAnotherMember();
		Product product = createProduct(member);
		ChatRoom chatRoom = FixtureFactory.createChatRoom(otherMember, product);
		ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
		Message message = FixtureFactory.createMessage(savedChatRoom.getUuid(), otherMember, "얼마에요?");
		messageRepository.save(message);
		Message message1 = FixtureFactory.createMessage(savedChatRoom.getUuid(), member, "얼마 원하세요??");
		messageRepository.save(message1);
		Message message2 = FixtureFactory.createMessage(savedChatRoom.getUuid(), member, "나눔해주세용");
		messageRepository.save(message2);

		// when
		var response = readMessages(otherMember.getId(), savedChatRoom.getUuid());

		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
			softAssertions.assertThat(response.jsonPath().getList("", MessageReadResponse.class).get(0).getSenderId())
				.isEqualTo(otherMember.getId());
		});
	}

	private ExtractableResponse<Response> readMessages(Long memberId, String roomId) {
		return RestAssured
			.given().log().all()
			.queryParam("roomId", roomId)
			.header(HttpHeaders.AUTHORIZATION,
				"Bearer " + jwtTokenProvider.createAccessToken(Map.of("memberId", memberId)))
			.when()
			.get("/api/chat-room/messages")
			.then().log().all()
			.extract();
	}

	private Member singUp() {
		return memberRepository.save(FixtureFactory.createMember());
	}

	private Member signupAnotherMember() {
		return memberRepository.save((FixtureFactory.createAnotherMember()));
	}

	private Product createProduct(Member member) throws IOException {
		ProductCreateRequest productCreateRequest = FixtureFactory.productCreateRequestWithMultipartFile();
		List<String> imageUrls = imageService.uploadProductImages(productCreateRequest.getImages());
		Category category = categoryRepository.findById(productCreateRequest.getCategoryId())
			.orElseThrow(CategoryNotFoundException::new);
		Address address = addressRepository.findById(productCreateRequest.getAddressId())
			.orElseThrow(AddressNotFoundException::new);
		Status status = statusRepository.findById(1L).orElseThrow(StatusNotFoundException::new);
		Product product = productRepository.save(
			productCreateRequest.toEntity(member, category, address, status, imageUrls.get(0)));
		imageService.create(product, imageUrls);
		return product;
	}
}
