package com._8attery.seesaw.dto.api.response;

import com._8attery.seesaw.domain.project_record.ProjectRecord;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProjectRecordResponseDto {
    @JsonProperty("record_id")
    private Long recordId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("question")
    private String question;
    @JsonProperty("contents")
    private String contents;

    @Builder
    public ProjectRecordResponseDto(Long recordId, LocalDateTime createdAt, String question, String contents) {
        this.recordId = recordId;
        this.createdAt = createdAt;
        this.question = question;
        this.contents = contents;
    }

    public static ProjectRecordResponseDto from(ProjectRecord projectRecord) {
        return ProjectRecordResponseDto.builder()
                .recordId(projectRecord.getId())
                .createdAt(projectRecord.getCreatedAt())
                .question(projectRecord.getQuestion())
                .contents(projectRecord.getContents())
                .build();
    }
}
