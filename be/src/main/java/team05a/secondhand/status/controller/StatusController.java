package team05a.secondhand.status.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05a.secondhand.status.data.dto.StatusResponse;
import team05a.secondhand.status.service.StatusService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statuses")
public class StatusController {

	private final StatusService statusService;

	@GetMapping
	public ResponseEntity<List<StatusResponse>> getStatuses() {
		List<StatusResponse> statusResponses = statusService.getStatuses();

		return ResponseEntity.ok()
			.body(statusResponses);
	}


}
