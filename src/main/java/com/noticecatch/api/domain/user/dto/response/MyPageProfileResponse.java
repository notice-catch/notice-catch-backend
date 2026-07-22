package com.noticecatch.api.domain.user.dto.response;

import com.noticecatch.api.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyPageProfileResponse {
    private final String nickname;
    private final String universityName;
    private final String departmentName;
    private final Integer grade;
    private final long scrapCount;
    private final long keywordCount;
    private final long readCount;

    public static MyPageProfileResponse of(User user, long scrapCount, long keywordCount, long readCount) {
        return new MyPageProfileResponse(
                user.getNickname(),
                user.getDepartment().getUniversity().getName(),
                user.getDepartment().getName(),
                user.getGrade(),
                scrapCount,
                keywordCount,
                readCount
        );
    }
}
