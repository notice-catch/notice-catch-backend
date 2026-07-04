package com.noticecatch.api.domain.user.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@Tag(name = "⚙️ User & MyPage", description = "온보딩 세팅, 개인 프로필 관리 및 알림 제어 API")
public interface UserControllerDocs {

    @Operation(summary = "온보딩 프로필 설정", description = "온보딩 단계에서 학과 소속 식별 및 학년을 세팅합니다.")
    ApiResponse<Void> setupOnboardingProfile(
            @Parameter(description = "등록할 학과의 고유 ID", example = "1") @PathVariable Long departmentId,
            @RequestBody Map<String, Object> request
    );

    @Operation(summary = "마이페이지 및 프로필 조회", description = "기본 인적사항 및 개인 지표 데이터를 추출합니다.")
    ApiResponse<Map<String, Object>> getProfile();

    @Operation(summary = "프로필 수정", description = "마이페이지 환경 내 소속 대학 ID, 학과 ID, 학년 사안을 변경 및 조율합니다.")
    @Parameters({
            @Parameter(name = "universityId", description = "수정 변경할 대학교 고유 ID", example = "3"),
            @Parameter(name = "departmentId", description = "수정 변경할 학과 고유 ID", example = "1")
    })
    ApiResponse<Map<String, Object>> updateProfile(
            @PathVariable Long universityId,
            @PathVariable Long departmentId,
            @RequestBody Map<String, Object> request
    );

    @Operation(summary = "알림 설정 조회", description = "키워드 알림 및 테마별 알림 수신 승인 여부를 체크합니다.")
    ApiResponse<Map<String, Object>> getAlarmSettings();

    @Operation(summary = "알림 설정 수정", description = "수신 타겟 알림 설정 제어 상태를 변경합니다.")
    ApiResponse<Map<String, Object>> updateAlarmSettings(@RequestBody Map<String, Object> request);

    @Operation(summary = "관심 키워드 관리(조회)", description = "본인이 커스텀 지정하거나 매핑한 관심사 키워드 세트를 가져옵니다.")
    ApiResponse<Map<String, Object>> getUserKeywords();

    @Operation(summary = "관심 키워드 관리(수정, 삭제, 추가 일괄 처리)", description = "기존 목록을 날리고 보낸 페이로드 리스트로 키워드를 전면 동기화합니다.")
    ApiResponse<Map<String, Object>> updateKeywords(@RequestBody Map<String, Object> request);
}
