package com.crazy.cc.controller.admin.auth;

import com.crazy.cc.controller.admin.auth.vo.AuthLoginReqVO;
import com.crazy.cc.controller.admin.auth.vo.AuthLoginRespVO;
import com.crazy.cc.framework.common.pojo.CommonResult;
import com.crazy.cc.service.auth.AdminAuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static com.crazy.cc.framework.common.pojo.CommonResult.success;

@Tag(name = "后台管理认证")
@RestController
@RequestMapping("/admin-api/system/auth")
@Validated
@Slf4j
public class AuthController {

    @Resource
    private AdminAuthService authService;

    @PostMapping("/login")
    @PermitAll
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        return success(authService.login(reqVO));
    }
}
