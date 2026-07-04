package com.noticecatch.api.domain.department.controller;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import com.noticecatch.api.global.apiPayload.code.GeneralSuccessCode;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class DepartmentController implements DepartmentControllerDocs {
    @Override
    @GetMapping("/universities/{universityId}/departments")
    public ApiResponse<Map<String, Object>> getDepartments(
            @PathVariable Long universityId,
            @RequestParam(name = "keyword", required = false) String keyword) {
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, null);
    }
}