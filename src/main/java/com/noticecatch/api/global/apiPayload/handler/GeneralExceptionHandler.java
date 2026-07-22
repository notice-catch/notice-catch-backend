package com.noticecatch.api.global.apiPayload.handler;

import com.noticecatch.api.global.apiPayload.ApiResponse;
import com.noticecatch.api.global.apiPayload.code.BaseErrorCode;
import com.noticecatch.api.global.apiPayload.code.GeneralErrorCode;
import com.noticecatch.api.global.apiPayload.exception.ProjectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

    // 도메인에서 명시적으로 던진 예외
    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<ApiResponse<Void>> handleProjectException(ProjectException e) {
        BaseErrorCode errorCode = e.getErrorCode();
        log.warn("[ProjectException] code={}, message={}", errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.onFailure(errorCode, null));
    }

    // @Valid 바디 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = collectFieldErrors(ex.getBindingResult().getFieldErrors());
        log.warn("[MethodArgumentNotValidException] errors={}", errors);
        return ResponseEntity.status(GeneralErrorCode.BAD_REQUEST.getStatus())
                .body(ApiResponse.onFailure(GeneralErrorCode.BAD_REQUEST, errors));
    }

    // 쿼리 파라미터(@ModelAttribute) 검증 실패
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleBindException(BindException ex) {
        Map<String, String> errors = collectFieldErrors(ex.getBindingResult().getFieldErrors());
        log.warn("[BindException] errors={}", errors);
        return ResponseEntity.status(GeneralErrorCode.BAD_REQUEST.getStatus())
                .body(ApiResponse.onFailure(GeneralErrorCode.BAD_REQUEST, errors));
    }

    // 파라미터 타입 불일치 (예: Long 파라미터에 문자열 전달)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {
        log.warn("[MethodArgumentTypeMismatchException] name={}, value={}", ex.getName(), ex.getValue());
        return ResponseEntity.status(GeneralErrorCode.BAD_REQUEST.getStatus())
                .body(ApiResponse.onFailure(GeneralErrorCode.BAD_REQUEST, null));
    }

    // 요청 JSON 파싱 실패
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("[HttpMessageNotReadableException] message={}", ex.getMessage());
        return ResponseEntity.status(GeneralErrorCode.BAD_REQUEST.getStatus())
                .body(ApiResponse.onFailure(GeneralErrorCode.BAD_REQUEST, null));
    }

    // 필수 쿼리 파라미터 누락
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex) {
        log.warn("[MissingServletRequestParameterException] parameter={}", ex.getParameterName());
        return ResponseEntity.status(GeneralErrorCode.BAD_REQUEST.getStatus())
                .body(ApiResponse.onFailure(GeneralErrorCode.BAD_REQUEST, null));
    }

    // 지원하지 않는 HTTP 메소드 호출
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex) {
        log.warn("[HttpRequestMethodNotSupportedException] method={}", ex.getMethod());
        return ResponseEntity.status(GeneralErrorCode.METHOD_NOT_ALLOWED.getStatus())
                .body(ApiResponse.onFailure(GeneralErrorCode.METHOD_NOT_ALLOWED, null));
    }

    // 존재하지 않는 엔드포인트 호출
    @ExceptionHandler({NoResourceFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity<ApiResponse<Void>> handleNoHandlerFound(Exception ex) {
        log.warn("[NoHandlerFoundException] message={}", ex.getMessage());
        return ResponseEntity.status(GeneralErrorCode.NOT_FOUND.getStatus())
                .body(ApiResponse.onFailure(GeneralErrorCode.NOT_FOUND, null));
    }

    // DB 유니크/제약조건 위반
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.warn("[DataIntegrityViolationException] message={}", ex.getMessage());
        return ResponseEntity.status(GeneralErrorCode.CONFLICT.getStatus())
                .body(ApiResponse.onFailure(GeneralErrorCode.CONFLICT, null));
    }

    // 그 외 예기치 못한 서버 오류
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        log.error("[UnhandledException] {}", ex.getMessage(), ex);
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(code, null));
    }

    private Map<String, String> collectFieldErrors(List<FieldError> fieldErrors) {
        Map<String, String> errors = new HashMap<>();
        fieldErrors.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return errors;
    }
}
