package team05a.secondhand.status.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import team05a.secondhand.AcceptanceTest;
import team05a.secondhand.errors.exception.StatusNotFoundException;
import team05a.secondhand.fixture.FixtureFactory;
import team05a.secondhand.status.data.entity.Status;

class StatusRepositoryTest extends AcceptanceTest {

	@Autowired
	private StatusRepository statusRepository;

	@DisplayName("등록된 상태를 찾는다.")
	@Test
	void findById() {
		//given & when
		Status status = FixtureFactory.createStatus();
		Status find = statusRepository.findById(status.getId()).orElseThrow(StatusNotFoundException::new);

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(find.getId()).isEqualTo(status.getId());
			softAssertions.assertThat(find.getName()).isEqualTo(status.getName());
		});
	}
}
