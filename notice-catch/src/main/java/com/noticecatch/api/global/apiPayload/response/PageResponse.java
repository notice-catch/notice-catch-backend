package com.noticecatch.api.global.apiPayload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse<T> {

    private final List<T> content;       // 데이터 목록
    private final int page;              // 현재 페이지 번호
    private final int size;              // 한 페이지당 데이터 개수
    private final boolean hasNext;        // 다음 페이지 존재 여부

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                !page.isLast()            // 마지막 페이지가 아니면 다음 페이지가 있어서 !isLast() 처리
        );
    }
}