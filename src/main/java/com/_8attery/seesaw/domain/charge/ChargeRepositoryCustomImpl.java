package com._8attery.seesaw.domain.charge;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com._8attery.seesaw.domain.charge.QCharge.charge;
import static com._8attery.seesaw.domain.value.QValue.value;

@RequiredArgsConstructor
@Repository
public class ChargeRepositoryCustomImpl implements ChargeRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countChargeByValueIdAndTerm(Long valueId, LocalDateTime startedAt, LocalDateTime endedAt) {

        return jpaQueryFactory
                .select(charge.id.count())
                .from(charge)
                .join(value).on(charge.value.id.eq(value.id))
                .fetchJoin()
                .where(value.id.eq(valueId), charge.createdAt.between(startedAt, endedAt))
                .groupBy(value.id)
                .fetchOne();
    }
}
