package com._8attery.seesaw.domain.project_emotion;

import com._8attery.seesaw.domain.project.Project;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SS_PROJECT_EMOTION")
@Entity
public class ProjectEmotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_emotion_id")
    private Long id;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "like_cnt", nullable = false)
    private Integer likeCnt; // 좋아요 개수

    @Column(name = "nice_cnt", nullable = false)
    private Integer niceCnt; // 멋져요 개수

    @Column(name = "idk_cnt", nullable = false)
    private Integer idkCnt; // 모르겠어요 개수

    @Column(name = "angry_cnt", nullable = false)
    private Integer angryCnt; // 화나요 개수

    @Column(name = "sad_cnt", nullable = false)
    private Integer sadCnt; // 슬퍼요 개수

    @Builder
    public ProjectEmotion(Project project, Integer likeCnt, Integer niceCnt, Integer idkCnt, Integer angryCnt, Integer sadCnt) {
        this.project = project;
        this.likeCnt = likeCnt;
        this.niceCnt = niceCnt;
        this.idkCnt = idkCnt;
        this.angryCnt = angryCnt;
        this.sadCnt = sadCnt;
    }
}
