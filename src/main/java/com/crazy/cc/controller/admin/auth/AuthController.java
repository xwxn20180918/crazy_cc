package com.crazy.cc.controller.admin.auth;

import cn.hutool.core.util.StrUtil;
import com.crazy.cc.controller.admin.auth.vo.AuthLoginReqVO;
import com.crazy.cc.controller.admin.auth.vo.AuthLoginRespVO;
import com.crazy.cc.framework.common.pojo.CommonResult;
import com.crazy.cc.framework.config.SecurityProperties;
import com.crazy.cc.service.auth.AdminAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.crazy.cc.framework.common.pojo.CommonResult.success;
import static com.crazy.cc.framework.core.util.SecurityFrameworkUtils.obtainAuthorization;

@Tag(name = "后台管理认证")
@RestController
@RequestMapping("/admin-api/system/auth")
@Validated
@Slf4j
public class AuthController {

    @Resource
    private AdminAuthService authService;

    @Resource
    private SecurityProperties securityProperties;

    @PostMapping("/login")
    @PermitAll
    @Operation(summary = "使用账号密码登录")
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        return success(authService.login(reqVO));
    }

    @PostMapping("/refresh-token")
    @PermitAll
    @Operation(summary = "刷新令牌")
    public CommonResult<AuthLoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return success(authService.refreshToken(refreshToken));
    }

    @PostMapping("/logout")
    @Operation(summary = "退出登录")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token);
        }
        return success(true);
    }
}
