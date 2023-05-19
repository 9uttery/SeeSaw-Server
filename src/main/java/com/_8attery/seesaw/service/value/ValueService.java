package com._8attery.seesaw.service.value;

import com._8attery.seesaw.domain.project.Project;
import com._8attery.seesaw.domain.value.ValueRepository;
import com._8attery.seesaw.dto.api.response.ValueInfoResponseDto;
import com._8attery.seesaw.dto.api.response.ValueResponseDto;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.exception.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com._8attery.seesaw.exception.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ValueService {

    private final ValueRepository valueRepository;

    // 사용자 가치 선택
    @Transactional
    public int set3Values(List<String> values, Long userId) throws BaseException {
        int count = checkValues(userId);
        if (count == 3) {
            // 가치 3개 이미 존재
            throw new BaseException(BaseResponseStatus.VALUES_EXIST);
        }

        int result = 1;
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (String value : values) {
            result = valueRepository.setUser3Values(value, currentDateTime, userId);
            if (result == 0)
                break;
        }

        return result;
    }

    public int checkValues(Long userId) {
        int count = valueRepository.checkValuesExist(userId);

        return count;
    }

    // 사용자 가치 조회
    public List<ValueResponseDto> get3Values(Long userId) {
        List<Object[]> values = valueRepository.getUser3Values(userId);

        List<ValueResponseDto> list = new ArrayList<>();

        for (Object[] row : values) {
            Number valueId = (Number) row[0];
            String valueName = (String) row[1];
            list.add(new ValueResponseDto(valueId.intValue(), valueName));
        }

        return list;
    }

    // 가치에 맞는 프로젝트, 고속 충전 조회
    public ValueInfoResponseDto getUserValueInfo(Long userId, Long valueId) throws BaseException {
        try {
            ValueInfoResponseDto res = new ValueInfoResponseDto();
            List<ValueInfoResponseDto.ValueProject> projectList = new ArrayList<>();

            // 1. 가치에 맞는 프로젝트 목록 가져오기 (프로젝트 이름, 시작/끝 날짜 필요)
            List<Project> findProjects = valueRepository.findValueProject(valueId);

            // 2. 가져온 프로젝트 목록 개수만큼 반복문 돌면서 projectList 에 프로젝트 이름, 진행률 설정
            for (Project pro : findProjects) {
                ValueInfoResponseDto.ValueProject dto = new ValueInfoResponseDto.ValueProject();
                dto.setProjectName(pro.getProjectName());

                LocalDateTime startedAt = pro.getStartedAt();
                LocalDateTime endedAt = pro.getEndedAt();
                Double rate = calculateProgressPercentage(startedAt, endedAt, 100.0);
                dto.setProgressRate(rate);

                projectList.add(dto);
            }

            // 3. res 에 projectList, 진행률 설정
            res.setProjectList(projectList);
            res.setCount(valueRepository.findValueCharge(valueId));

            return res;

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 프로젝트 진행률 계산
    public static double calculateProgressPercentage(LocalDateTime startDate, LocalDateTime endDate, Double progressRate) {
        LocalDateTime now = LocalDateTime.now();

        Duration fullPeriod = Duration.between(startDate, endDate);
        Duration progressedPeriod = Duration.between(startDate, now);

        long fullPeriodSeconds = fullPeriod.getSeconds();
        long progressedPeriodSeconds = progressedPeriod.getSeconds();

        double calculatedProgressRate = (double) progressedPeriodSeconds / fullPeriodSeconds * 100;
        double progressPercentage = Math.min(calculatedProgressRate, progressRate); // Cap the progress rate at the given progressRate value

        return Math.round(progressPercentage * 10) / 10.0; // Round to one decimal place
    }

//    public static double calculateProgressPercentage(LocalDateTime startDate, LocalDateTime endDate) {
//        LocalDateTime now = LocalDateTime.now();
//
//        Duration fullPeriod = Duration.between(startDate, endDate);
//        Duration progressedPeriod = Duration.between(startDate, now);
//
//        long fullPeriodSeconds = fullPeriod.getSeconds();
//        long progressedPeriodSeconds = progressedPeriod.getSeconds();
//
//        if (fullPeriodSeconds <= 0) {
//            return 100.0; // End date has already passed, return progress 100
//        }
//
//        double progressPercentage = (double) progressedPeriodSeconds / fullPeriodSeconds * 100;
//
//        return Math.round(progressPercentage * 10) / 10.0; // Round to one decimal place
//    }

}
