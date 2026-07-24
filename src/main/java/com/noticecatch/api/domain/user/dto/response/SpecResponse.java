package com.noticecatch.api.domain.user.dto.response;

import com.noticecatch.api.domain.user.entity.Spec;
import com.noticecatch.api.domain.user.entity.SpecCategory;

import java.time.LocalDate;

public record SpecResponse(
        Long specId,
        String category,
        String categoryTag,
        String title,
        String organization,
        LocalDate specDate
) {
    public static SpecResponse from(Spec spec) {
        return new SpecResponse(
                spec.getId(),
                spec.getCategory(),
                SpecCategory.from(spec.getCategory()).getTag(),
                spec.getTitle(),
                spec.getOrganization(),
                spec.getSpecDate()
        );
    }
}
