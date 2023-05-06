package com._8attery.seesaw.domain.battery_variation;

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
@Table(name = "SS_BATTERY_VARIATION")
@Entity
public class BatteryVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "battery_variation_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "battery_id")
    private Battery battery;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BatteryType type; // 유형

    @Column(name = "name")
    private String name; // 증감 내역 이름

    @Column(name = "variation_percentage")
    private Integer variationPercentage; // 증감량

    @Column(name = "create_at")
    private LocalDateTime CreatedAt; // 생성 날짜

    @Builder
    public BatteryVariation(BatteryType type, String name, Integer variationPercentage, LocalDateTime createdAt) {
        this.type = type;
        this.name = name;
        this.variationPercentage = variationPercentage;
        CreatedAt = createdAt;
    }
}
