package com._8attery.seesaw.exception;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    // Common

    // User


    // Value
    VALUES_EXIST(false, 2010, "사용자의 올해 가치 3개가 이미 존재합니다."),

    // Project
    POSTS_EMPTY_POST_ID(false, 2020, "프로젝트 아이디 값을 확인해주세요."),
    USERS_FAILED_POST_ID(false, 2021, "해당 포스트를 작성한 유저가 아닙니다."),
    DELETE_FAIL_POST(false, 2022, "게시물 삭제를 실패했습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

}