package com._8attery.seesaw.service.charge;

import com._8attery.seesaw.domain.charge.ChargeRepository;
import com._8attery.seesaw.dto.api.response.ChargeResponseDto;
import com._8attery.seesaw.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com._8attery.seesaw.exception.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChargeService {

    private final ChargeRepository chargeRepository;

    // 고속충전 사용
    @Transactional
    public ChargeResponseDto setUserCharge(Long userId, Long valueId, String chargeName, LocalDateTime createdAt) throws BaseException {
        try {
            // 고속충전 사용
            chargeRepository.addUserCharge(userId, valueId, chargeName, createdAt);
            // 오늘 고속충전 여부 수정
            chargeRepository.updateIsCharged(userId);
            // 사용자 배터리 + 10
            chargeRepository.updateUserBattery(userId);

            // 배터리 증감 내역 레코드 추가
            String type = "CHARGE";
            Long batteryId = chargeRepository.findUserBattery(userId);
            chargeRepository.addUserVariation(batteryId, createdAt, type);

            Optional<ChargeResponseDto> chargeResponseDto = chargeRepository.findUserCharge(userId, valueId, chargeName, createdAt);
            return chargeResponseDto.orElse(new ChargeResponseDto(0L, null, null));

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 고속충전 조회
    public ChargeResponseDto getUserCharge(Long userId) throws BaseException {
        try {

            return chargeRepository.findTodayCharge(userId);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
