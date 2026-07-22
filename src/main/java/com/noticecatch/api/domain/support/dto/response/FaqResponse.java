package com.noticecatch.api.domain.support.dto.response;

import com.noticecatch.api.domain.support.entity.Faq;

public record FaqResponse(
        Long faqId,
        String category,
        String question,
        String answer
) {
    public static FaqResponse from(Faq faq) {
        return new FaqResponse(faq.getId(), faq.getCategory(), faq.getQuestion(), faq.getAnswer());
    }
}
