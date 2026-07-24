package com.noticecatch.api.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.noticecatch.api.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmSettingResponse {

    @JsonProperty("isAll")
    private final boolean isAll;

    @JsonProperty("isClosing")
    private final boolean isClosing;

    @JsonProperty("isKeyword")
    private final boolean isKeyword;

    private final boolean scholarship;
    private final boolean extracurricular;
    private final boolean academic;
    private final boolean employment;

    public static AlarmSettingResponse from(User user) {
        return new AlarmSettingResponse(
                Boolean.TRUE.equals(user.getAllNotification()),
                Boolean.TRUE.equals(user.getClosingNotification()),
                Boolean.TRUE.equals(user.getKeywordNotification()),
                Boolean.TRUE.equals(user.getScholarship()),
                Boolean.TRUE.equals(user.getExtracurricular()),
                Boolean.TRUE.equals(user.getAcademic()),
                Boolean.TRUE.equals(user.getEmployment())
        );
    }
}
