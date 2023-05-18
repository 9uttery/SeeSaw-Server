package com._8attery.seesaw.domain.battery_history;

import com._8attery.seesaw.domain.battery.Battery;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SS_BATTERY_HISTORY")
@Entity
public class BatteryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "battery_history_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "battery_id")
    private Battery battery;

    @Column(name = "battery_percentage", nullable = false)
    private Integer batteryPercentage; // 배터리 퍼센트

    @Column(name = "sleep_time")
    private Integer sleepTime; // 수면 시간

    @Column(name = "activity")
    private Integer activity; // 활동량

    @Column(name = "activity_goal")
    private Integer activityGoal; // 목표 활동량

    @Column(name = "sleep_goal")
    private Integer sleepGoal; // 목표 수면량

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 생성 날짜

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt; // 수정 날짜

    @Builder
    public BatteryHistory(Integer batteryPercentage, Integer sleepTime, Integer activity, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.batteryPercentage = batteryPercentage;
        this.sleepTime = sleepTime;
        this.activity = activity;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
