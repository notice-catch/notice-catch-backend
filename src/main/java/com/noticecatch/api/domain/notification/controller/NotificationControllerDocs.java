package com.noticecatch.api.domain.notification.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Tag(name = "🔔 Notification", description = "기기 토큰 등록 및 알림 수신 히스토리 API")
public interface NotificationControllerDocs {
    @Operation(summary = "유저 기기 토큰 저장", description = "FCM 알림 타겟팅을 위해 모바일 디바이스 푸시 토큰을 수집합니다.")
    ApiResponse<Void> saveDeviceToken(@RequestBody Map<String, Object> request);

    @Operation(summary = "알림함 목록 조회", description = "유저에게 전송되었던 알림 리스트를 최신순 페이징하여 조회합니다.")
    ApiResponse<Map<String, Object>> getNotifications(@RequestParam int page, @RequestParam int size);

    @Operation(summary = "알림함 전체 읽기", description = "미확인 상태의 전송 알림 리스트를 전부 읽음 일괄 처리합니다.")
    ApiResponse<Void> readAllNotifications();
}
