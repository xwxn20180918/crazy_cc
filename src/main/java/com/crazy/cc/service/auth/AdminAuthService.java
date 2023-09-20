package com.crazy.cc.service.auth;

import com.crazy.cc.controller.admin.auth.vo.AuthLoginRespVO;
import com.crazy.cc.controller.admin.auth.vo.AuthLoginReqVO;

import javax.validation.Valid;

public interface AdminAuthService {
    AuthLoginRespVO login(@Valid AuthLoginReqVO reqVO);

    AuthLoginRespVO refreshToken(String refreshToken);

    void logout(String token);
}
