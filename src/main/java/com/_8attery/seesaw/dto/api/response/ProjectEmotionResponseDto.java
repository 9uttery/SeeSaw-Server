package com._8attery.seesaw.dto.api.response;

import com._8attery.seesaw.domain.project_emotion.ProjectEmotion;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectEmotionResponseDto {
    @JsonProperty("like_cnt")
    private Integer likeCnt;
    @JsonProperty("nice_cnt")
    private Integer niceCnt;
    @JsonProperty("idk_cnt")
    private Integer idkCnt;
    @JsonProperty("angry_cnt")
    private Integer angryCnt;
    @JsonProperty("sad_cnt")
    private Integer sadCnt;

    @Builder
    public ProjectEmotionResponseDto(Integer likeCnt, Integer niceCnt, Integer idkCnt, Integer angryCnt, Integer sadCnt) {
        this.likeCnt = likeCnt;
        this.niceCnt = niceCnt;
        this.idkCnt = idkCnt;
        this.angryCnt = angryCnt;
        this.sadCnt = sadCnt;
    }

    public static ProjectEmotionResponseDto from(ProjectEmotion projectEmotion) {
        return ProjectEmotionResponseDto.builder()
                .likeCnt(projectEmotion.getLikeCnt())
                .niceCnt(projectEmotion.getNiceCnt())
                .idkCnt(projectEmotion.getIdkCnt())
                .angryCnt(projectEmotion.getAngryCnt())
                .sadCnt(projectEmotion.getSadCnt())
                .build();
    }
}
