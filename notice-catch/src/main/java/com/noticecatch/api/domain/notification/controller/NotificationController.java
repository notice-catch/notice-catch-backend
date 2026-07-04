package com.noticecatch.api.domain.notification.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import com.noticecatch.api.global.apiPayload.code.GeneralSuccessCode;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class NotificationController implements NotificationControllerDocs {
    @Override
    @PostMapping("/users/device")
    public ApiResponse<Void> saveDeviceToken(@RequestBody Map<String, Object> request) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @GetMapping("/notifications")
    public ApiResponse<Map<String, Object>> getNotifications(
            @RequestParam int page,
            @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }

    @Override
    @PatchMapping("/notifications/read")
    public ApiResponse<Void> readAllNotifications() {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}
