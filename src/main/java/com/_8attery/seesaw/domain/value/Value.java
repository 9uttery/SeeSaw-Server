package com._8attery.seesaw.domain.value;

import com._8attery.seesaw.domain.charge.Charge;
import com._8attery.seesaw.domain.project.Project;
import com._8attery.seesaw.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SS_VALUE")
@Entity
public class Value {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "value_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name", nullable = false)
    private String name; // 가치 이름

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 생성 날짜

    @OneToOne(mappedBy = "value", fetch = LAZY)
    private Charge charge;

    @OneToOne(mappedBy = "value", fetch = LAZY)
    private Project project;

    @Builder
    public Value(String name, LocalDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
