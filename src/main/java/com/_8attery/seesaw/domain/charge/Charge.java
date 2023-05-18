package com._8attery.seesaw.domain.charge;

import com._8attery.seesaw.domain.user.User;
import com._8attery.seesaw.domain.value.Value;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SS_CHARGE")
@Entity
public class Charge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charge_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "value_id")
    private Value value;

    @Column(name = "name", nullable = false)
    private String name; // 고속충전 이름

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 생성 날짜

    @Builder
    public Charge(String name, LocalDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
