package com.noticecatch.api.domain.support.service;

import com.noticecatch.api.domain.support.dto.response.SupportNoticeResponse;
import com.noticecatch.api.domain.support.entity.SupportNotice;
import com.noticecatch.api.domain.support.repository.SupportNoticeRepository;
import com.noticecatch.api.global.apiPayload.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupportNoticeService {

    private final SupportNoticeRepository supportNoticeRepository;

    public PageResponse<SupportNoticeResponse> getSupportNotices(Pageable pageable) {
        Page<SupportNoticeResponse> page = supportNoticeRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(SupportNoticeResponse::from);
        return PageResponse.from(page);
    }
}
