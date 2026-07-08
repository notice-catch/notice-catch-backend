package com.noticecatch.api.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // API 문서 기본 정보 설정
        Info info = new Info()
                .title("공지캐치 (Notice-Catch) API 명세서")
                .description("대학 공지사항 알림 서비스 '공지캐치'의 백엔드 API 명세서입니다.")
                .version("v1.0.0");

        return new OpenAPI()
                .info(info);
    }
}