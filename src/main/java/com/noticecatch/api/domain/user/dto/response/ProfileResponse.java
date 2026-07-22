package com.noticecatch.api.domain.user.dto.response;

import com.noticecatch.api.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileResponse {
    private final String nickname;
    private final String universityName;
    private final String departmentName;
    private final Integer grade;

    public static ProfileResponse from(User user) {
        return new ProfileResponse(
                user.getNickname(),
                user.getDepartment().getUniversity().getName(),
                user.getDepartment().getName(),
                user.getGrade()
        );
    }
}
