package com.studyhub.study_member.common.web.context;

import com.studyhub.study_member.common.exception.BadParameter;
import com.studyhub.study_member.common.exception.NotFound;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class GatewayRequestHeaderUtils {
    public static String getRequestHeaderParamAsString(String key) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest().getHeader(key);
    }

    public static String getUserId() {
        return getRequestHeaderParamAsString("X-Auth-UserId");
    }

    public static String getUserName() {
        return getRequestHeaderParamAsString("X-Auth-UserName");
    }

    public static Long getUserIdOrThrowException() {
        String userId = getUserId();
        if(userId == null) {
            throw new NotFound("헤더에 userId 정보가 없습니다.");
        }

        try {
            return Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new BadParameter("userId 헤더가 올바른 숫자 형식이 아닙니다.");
        }
    }

    public static String getCUserNameOrThrowException() {
        String userName = getUserName();
        if (userName == null) {
            throw new NotFound("헤더에 사용자 이름 정보가 없습니다.");
        }
        return userName;
    }
}
