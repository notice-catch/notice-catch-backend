package com.noticecatch.api.domain.support.service;

import com.noticecatch.api.domain.support.dto.response.FaqResponse;
import com.noticecatch.api.domain.support.repository.FaqRepository;
import com.noticecatch.api.global.apiPayload.response.ListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FaqService {

    private final FaqRepository faqRepository;

    public ListResponse<FaqResponse> getFaqs(String category) {
        List<FaqResponse> responses = faqRepository.findAllByCategoryOrderByCreatedAtDesc(category).stream()
                .map(FaqResponse::from)
                .toList();
        return ListResponse.from(responses);
    }
}
