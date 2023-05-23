package com._8attery.seesaw.domain.battery;

import com._8attery.seesaw.domain.battery_history.BatteryHistory;
import com._8attery.seesaw.domain.battery_variation.BatteryVariation;
import com._8attery.seesaw.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SS_BATTERY")
@Entity
public class Battery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "battery_id")
    private Long id;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "cur_battery", nullable = false)
    private Integer curBattery; // 현재 배터리

    @Column(name = "cur_activity")
    private Integer curActivity; // 현재 활동량

    @Column(name = "cur_sleep")
    private Integer curSleep; // 어제 수면량

    @Column(name = "activity_goal")
    private Integer activityGoal; // 목표 활동량

    @Column(name = "sleep_goal")
    private Integer sleepGoal; // 목표 수면량

    @Column(name = "is_charged")
    private Boolean isCharged; // 오늘 고속 충전 여부

    @OneToMany(mappedBy = "battery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BatteryHistory> batteryHistories = new ArrayList<>();

    @OneToMany(mappedBy = "battery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BatteryVariation> batteryVariations = new ArrayList<>();

    @Builder
    public Battery(Integer curBattery, Integer curActivity, Integer curSleep, Integer activityGoal, Integer sleepGoal, Boolean isCharged) {
        this.curBattery = curBattery;
        this.curActivity = curActivity;
        this.curSleep = curSleep;
        this.activityGoal = activityGoal;
        this.sleepGoal = sleepGoal;
        this.isCharged = isCharged;
    }
}
