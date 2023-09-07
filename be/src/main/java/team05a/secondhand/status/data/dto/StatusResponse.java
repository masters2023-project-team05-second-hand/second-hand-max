package team05a.secondhand.status.data.dto;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.status.data.entity.Status;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StatusResponse {

    private final Long id;
    private final String type;

    @Builder
    private StatusResponse(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public static List<StatusResponse> from(List<Status> statuses) {
        return statuses.stream()
            .map(StatusResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    private static StatusResponse from(Status status) {
        return StatusResponse.builder()
            .id(status.getId())
            .type(status.getName())
            .build();
    }
}
