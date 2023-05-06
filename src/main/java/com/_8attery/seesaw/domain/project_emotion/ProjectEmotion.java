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

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "like_cnt", nullable = false)
    private Integer likeCnt; // 좋아요 개수

    @Column(name = "nice_cnt", nullable = false)
    private Integer niceCnt; // 멋져요 개수

    @Builder
    public ProjectEmotion(Integer likeCnt, Integer niceCnt) {
        this.likeCnt = likeCnt;
        this.niceCnt = niceCnt;
    }
}
