package com._8attery.seesaw.domain.project_remembrance;

import com._8attery.seesaw.domain.project.Project;
import com._8attery.seesaw.domain.project_qna.ProjectQna;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SS_PROJECT_REMEMBRANCE")
@Entity
public class ProjectRemembrance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_remembrance_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private RemembranceType type; // 유형

    @Column(name = "date", nullable = false)
    private LocalDateTime date; // 회고 날짜

    @OneToMany(mappedBy = "projectRemembrance")
    private List<ProjectQna> projectQnas = new ArrayList<>();

    @Builder
    public ProjectRemembrance(RemembranceType type, LocalDateTime date) {
        this.type = type;
        this.date = date;
    }
}
