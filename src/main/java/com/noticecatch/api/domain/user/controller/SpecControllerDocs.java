package com.noticecatch.api.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "📝 Spec Log", description = "유저 개인 취업 준비 스펙 CRUD 관리 API")
public interface SpecControllerDocs {

    @Operation(summary = "스펙 로그 목록 조회", description = "유형별 누적 통계 헤더와 세부 이력 목록 데이터를 페이징하여 보여줍니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 예시 (COMMON200)",
                                    value = """
                    {
                      "isSuccess": true,
                      "code": "COMMON200",
                      "message": "성공",
                      "result": {
                        "categoryCounts": {
                          "allCount": 15,
                          "licenseCount": 3,
                          "awardCount": 1,
                          "activityCount": 6,
                          "languageCount": 2,
                          "internCount": 1,
                          "etcCount": 2
                        },
                        "content": [
                          {
                            "specId": 104,
                            "category": "LICENSE",
                            "categoryTag": "자격증",
                            "title": "정보처리기사",
                            "organization": "한국산업인력공단",
                            "specDate": "2026-05-20"
                          },
                          {
                            "specId": 89,
                            "category": "AWARD",
                            "categoryTag": "수상",
                            "title": "2026 대학생 공공데이터 활용 창업경진대회 최우수상",
                            "organization": "행정안전부",
                            "specDate": "2026-04-15"
                          }
                        ],
                        "page": 0,
                        "size": 20,
                        "hasNext": false
                      }
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "실패 예시 (AUTH401)",
                                    value = """
                    {
                      "isSuccess": false,
                      "code": "AUTH401",
                      "message": "인증 자격 증명이 유효하지 않거나 만료되었습니다. 다시 로그인해 주세요.",
                      "result": null
                    }
                    """
                            )
                    )
            )
    })
    com.noticecatch.api.global.apiPayload.ApiResponse<Map<String, Object>> getSpecs(
            @RequestParam String category,
            @RequestParam String sort,
            @RequestParam int page,
            @RequestParam int size
    );

    @Operation(summary = "스펙 로그 추가", description = "새 기록 이력을 기입하고 갱신 피드를 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 예시 (COMMON200)",
                                    value = """
                    {
                      "isSuccess": true,
                      "code": "COMMON200",
                      "message": "성공",
                      "result": {
                        "content": [
                          {
                            "specId": 105,
                            "category": "LANGUAGE",
                            "categoryTag": "어학점수",
                            "title": "토익 (TOEIC)",
                            "organization": "YBM 한국TOEIC위원회",
                            "specDate": "2026-06-10"
                          },
                          {
                            "specId": 104,
                            "category": "LICENSE",
                            "categoryTag": "자격증",
                            "title": "정보처리기사",
                            "organization": "한국산업인력공단",
                            "specDate": "2026-05-25"
                          }
                        ],
                        "page": 0,
                        "size": 20,
                        "hasNext": false
                      }
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "실패 예시 (SPEC4002)",
                                    value = """
                    {
                      "isSuccess": false,
                      "code": "SPEC4002",
                      "message": "스펙 제목, 카테고리, 취득일(활동일)은 필수 입력 항목입니다.",
                      "result": null
                    }
                    """
                            )
                    )
            )
    })
    com.noticecatch.api.global.apiPayload.ApiResponse<Map<String, Object>> addSpec(@RequestParam int page, @RequestParam int size, @RequestBody Map<String, Object> request);

    @Operation(summary = "스펙 로그 수정", description = "특정 고유번호의 취업 스펙 기록 사안을 수정 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 예시 (COMMON200)",
                                    value = """
                    {
                      "isSuccess": true,
                      "code": "COMMON200",
                      "message": "성공",
                      "result": {
                        "categoryCounts": {
                          "allCount": 15,
                          "licenseCount": 3,
                          "awardCount": 1,
                          "activityCount": 6,
                          "languageCount": 2,
                          "internCount": 1,
                          "etcCount": 2
                        },
                        "content": [
                          {
                            "specId": 104,
                            "category": "LICENSE",
                            "categoryTag": "자격증",
                            "title": "정보처리기사",
                            "organization": "한국산업인력공단",
                            "specDate": "2026-05-25"
                          },
                          {
                            "specId": 89,
                            "category": "AWARD",
                            "categoryTag": "수상",
                            "title": "2026 대학생 공공데이터 활용 창업경진대회 최우수상",
                            "organization": "행정안전부",
                            "specDate": "2026-04-15"
                          },
                          {
                            "specId": 107,
                            "category": "LANGUAGE",
                            "categoryTag": "어학",
                            "title": "토익 (TOEIC) (수정)",
                            "organization": "YBM 한국TOEIC위원회",
                            "specDate": "2026-06-10"
                          }
                        ],
                        "page": 0,
                        "size": 20,
                        "hasNext": false
                      }
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "수정 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "실패 예시 (SPEC4041)",
                                    value = """
                    {
                      "isSuccess": false,
                      "code": "SPEC4041",
                      "message": "해당 스펙 로그 식별자(ID)를 찾을 수 없습니다.",
                      "result": null
                    }
                    """
                            )
                    )
            )
    })
    com.noticecatch.api.global.apiPayload.ApiResponse<Map<String, Object>> updateSpec(
            @Parameter(description = "수정하려는 스펙 고유 ID", example = "104") @PathVariable Long specId,
            @RequestBody Map<String, Object> request
    );

    @Operation(summary = "스펙 로그 삭제", description = "단일 스펙을 삭제 후 정리된 최신 현황 목록을 재반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 예시 (COMMON200)",
                                    value = """
                    {
                      "isSuccess": true,
                      "code": "COMMON200",
                      "message": "성공",
                      "result": {
                        "categoryCounts": {
                          "allCount": 15,
                          "licenseCount": 3,
                          "awardCount": 1,
                          "activityCount": 6,
                          "languageCount": 2,
                          "internCount": 1,
                          "etcCount": 2
                        },
                        "content": [
                          {
                            "specId": 104,
                            "category": "LICENSE",
                            "categoryTag": "자격증",
                            "title": "정보처리기사",
                            "organization": "한국산업인력공단",
                            "specDate": "2026-05-20"
                          }
                        ],
                        "page": 0,
                        "size": 20,
                        "hasNext": false
                      }
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "삭제 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "실패 예시 (SPEC4041)",
                                    value = """
                    {
                      "isSuccess": false,
                      "code": "SPEC4041",
                      "message": "이미 삭제되었거나 존재하지 않는 스펙 로그 식별자(ID)입니다.",
                      "result": null
                    }
                    """
                            )
                    )
            )
    })
    com.noticecatch.api.global.apiPayload.ApiResponse<Map<String, Object>> deleteSpec(
            @Parameter(description = "삭제하려는 스펙 고유 ID", example = "104") @PathVariable Long specId
    );
}
