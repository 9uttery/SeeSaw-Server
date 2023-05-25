package com._8attery.seesaw.domain.value;

import java.time.LocalDateTime;
import java.util.List;

public interface ValueRepositoryCustom {
    List<Value> getValueInProjectYear(Long userId, LocalDateTime startedAt, LocalDateTime endedAt);
}
