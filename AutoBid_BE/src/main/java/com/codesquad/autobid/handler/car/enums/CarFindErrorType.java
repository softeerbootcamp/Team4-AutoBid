package com.codesquad.autobid.handler.car.enums;

import java.util.Arrays;

public enum CarFindErrorType {
    INVALID_REQUEST_BODY(4002, "호출 인자 값 오류"),
    INVALID_AUTHORIZATION_HEADER(4011, "헤더 값 오류"),
    INVALID_SESSION(4012, "세션 값 오류"),
    NO_SERVICE_TERM(4014, "약관 미등록 상태    developers@hyundai.com에 문의하여 등록 요청해야 합니다."),
    UNAUTHORIZED_CLIENT(4016, "서비스 인증 값 오류"),
    UNREGISTERED_USER(4043, "CCS 미가입 고객의 정보를 요청함"),
    NO_DATA(4045, "장기 미운행 차량, 단말 데이터 전송 오류 등으로 데이터 제공 불가"),
    NO_REGISTERED_VEHICLES(4046, "차량 미보유 고객으로 데이터 제공 불가"),
    PRE_OPERATION_IS_REQUIRED(4120, "선 호출 필요한 API를 호출하지 않은 경우 발생"),
    INTERNAL_SERVER_ERROR(5001, "API 내부 에러"),
    INTERNAL_SERVER_PERMISSION_ERROR(5004, "API 내부 에러"),
    NO_AGREEMENT_ERROR(5005, "사용자가 정보 제공 미동의 상태로 데이터 제공 불가"),
    NO_PERMISSION_ERROR(5006, "API 호출 권한 없음"),
    SERVICE_NOT_REGISTERED_ERROR(5007, "등록된 서비스가 아닌 경우 발생"),
    SERVICE_NOT_DEFINED_ERROR(5008, "서비스 정보 오류"),
    UNAVAILABLE_REMOTE_CONTROL(5031, "차량 상태에 의해 원격제어가 불가능한 경우 발생"),
    SERVICE_UNAVAILABLE(5032, "데이터 조회 불가 차량으로 서비스 지원이 불가한 경우 발생"),
    GATEWAY_TIMEOUT(5041, "타임아웃 에러"),
    UNDEFINED_ERROR(9999, "API 내부 에러");

    private int code;
    private String msg;

    CarFindErrorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static CarFindErrorType findByErrorCode(int code) {
        return Arrays.stream(CarFindErrorType.values())
                .filter(carFindErrorCode -> carFindErrorCode.code == code)
                .findAny()
                .orElse(UNDEFINED_ERROR);
    }

    public boolean isCarNotExistError() {
        return code == NO_DATA.code || code == NO_REGISTERED_VEHICLES.code;
    }
}
