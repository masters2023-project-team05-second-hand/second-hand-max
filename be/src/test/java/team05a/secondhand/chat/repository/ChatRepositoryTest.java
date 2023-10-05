package team05a.secondhand.chat.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import team05a.secondhand.AcceptanceTest;
import team05a.secondhand.address.data.entity.Address;
import team05a.secondhand.address.repository.AddressRepository;
import team05a.secondhand.category.data.entity.Category;
import team05a.secondhand.category.repository.CategoryRepository;
import team05a.secondhand.chat.data.dto.ChatRoomListResponse;
import team05a.secondhand.chat.data.entity.ChatRoom;
import team05a.secondhand.chat.data.entity.Message;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.member.data.entity.Member;
import team05a.secondhand.member.repository.MemberRepository;
import team05a.secondhand.product.data.entity.Product;
import team05a.secondhand.product.repository.ProductRepository;
import team05a.secondhand.status.data.entity.Status;
import team05a.secondhand.status.repository.StatusRepository;

public class ChatRepositoryTest extends AcceptanceTest {

	@Autowired
	private ChatRoomRepository chatRoomRepository;
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private StatusRepository statusRepository;

	@Transactional
	@DisplayName("채팅방 목록 조회를 한다.")
	@Test
	void findChatRoomList() {
		//given
		Member member = memberRepository.save(FixtureFactory.createMember());
		Member otherMember = memberRepository.save(FixtureFactory.createAnotherMember());
		Address address = addressRepository.findById(1L).orElseThrow();
		Category category = categoryRepository.findById(1L).orElseThrow();
		Status status = statusRepository.findById(1L).orElseThrow();
		Product product = FixtureFactory.createProductForRepo(member, address, category, status);
		Product save = productRepository.save(product);
		ChatRoom chatRoom = FixtureFactory.createChatRoom(otherMember, save);
		ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
		Message message = FixtureFactory.createMessage(savedChatRoom.getUuid(), otherMember, "얼마에요?");
		Message savedMessage = messageRepository.save(message);

		// when
		List<ChatRoomListResponse> response = chatRoomRepository.findChatRoomList(member.getId(), 0L);

		//then
		assertThat(response.get(0).getUnreadMessageCount()).isEqualTo(1);
		assertThat(response.get(0).getMessage().getLastMessage()).isEqualTo(savedMessage.getContent());
	}
}
