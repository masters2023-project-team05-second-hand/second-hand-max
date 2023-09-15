package team05a.secondhand.status.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.status.data.dto.StatusResponse;
import team05a.secondhand.status.repository.StatusRepository;

@Service
@RequiredArgsConstructor
public class StatusService {

	private final StatusRepository statusRepository;

	public List<StatusResponse> getStatuses() {
		return StatusResponse.from(statusRepository.findAll());
	}
}
