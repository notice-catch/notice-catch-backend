package com.noticecatch.api.domain.notice.service;

import com.noticecatch.api.domain.notice.dto.response.NoticeDetailResponse;
import com.noticecatch.api.domain.notice.entity.Notice;
import com.noticecatch.api.domain.notice.repository.NoticeRepository;
import com.noticecatch.api.global.apiPayload.code.GeneralErrorCode;
import com.noticecatch.api.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeDetailResponse getNoticeDetail(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));
        // 만약 GeneralErrorCode에 NOT_FOUND가 없다면, GeneralErrorCode. 까지 친 뒤
        // Ctrl + Space로 나오는 404 관련 에러 코드로 골라주시면 됩니다!

        return NoticeDetailResponse.from(notice);
    }
}