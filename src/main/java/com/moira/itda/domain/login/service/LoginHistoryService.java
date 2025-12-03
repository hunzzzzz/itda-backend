package com.moira.itda.domain.login.service;

import com.moira.itda.domain.login.mapper.LoginMapper;
import com.moira.itda.global.entity.LoginHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Service
public class LoginHistoryService {
    private final LoginMapper loginMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLoginFailureHistory(String userId, String ipAddress, String userAgent) {
        LoginHistory loginHistory = LoginHistory.builder()
                .userId(userId)
                .successYn("N")
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .loginAt(ZonedDateTime.now())
                .build();

        loginMapper.insertLoginHistory(loginHistory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveLoginSuccessHistory(String userId, String ipAddress, String userAgent) {
        LoginHistory loginHistory = LoginHistory.builder()
                .userId(userId)
                .successYn("Y")
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .loginAt(ZonedDateTime.now())
                .build();

        loginMapper.insertLoginHistory(loginHistory);
    }
}
