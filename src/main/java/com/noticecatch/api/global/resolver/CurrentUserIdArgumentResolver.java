package com.noticecatch.api.global.resolver;

import com.noticecatch.api.global.apiPayload.code.GeneralErrorCode;
import com.noticecatch.api.global.apiPayload.exception.ProjectException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * TODO(회원/인증 연동): 아직 JWT 인증 필터가 없어 임시로 "X-USER-ID" 요청 헤더로 로그인 유저를 지정한다.
 * 회원팀이 JWT 필터를 완성하면 이 클래스만 SecurityContext에서 유저 ID를 꺼내오도록 교체하면 되고,
 * {@code @CurrentUserId} 를 사용하는 컨트롤러 쪽 코드는 손댈 필요가 없다.
 */
@Component
public class CurrentUserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String TEMP_USER_ID_HEADER = "X-USER-ID";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUserId.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                   NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String header = webRequest.getHeader(TEMP_USER_ID_HEADER);
        if (header == null || header.isBlank()) {
            throw new ProjectException(GeneralErrorCode.UNAUTHORIZED);
        }
        try {
            return Long.parseLong(header);
        } catch (NumberFormatException e) {
            throw new ProjectException(GeneralErrorCode.UNAUTHORIZED);
        }
    }
}
