package com._8attery.seesaw.domain.value;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com._8attery.seesaw.domain.user.QUser.user;
import static com._8attery.seesaw.domain.value.QValue.value;

@RequiredArgsConstructor
@Repository
public class ValueRepositoryCustomImpl implements ValueRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Value> getValueInProjectYear(Long userId, LocalDateTime startedAt, LocalDateTime endedAt) {
        return jpaQueryFactory.select(value)
                .from(value)
                .join(user).on(value.user.id.eq(user.id))
                .fetchJoin()
                .where(user.id.eq(userId), value.createdAt.between(startedAt, endedAt))
                .fetch();
    }
}
