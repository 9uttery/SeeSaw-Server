package com._8attery.seesaw.domain.charge;

import java.time.LocalDateTime;

public interface ChargeRepositoryCustom {
    Long countChargeByValueIdAndTerm(Long valueId, LocalDateTime startedAt, LocalDateTime endedAt);
}
