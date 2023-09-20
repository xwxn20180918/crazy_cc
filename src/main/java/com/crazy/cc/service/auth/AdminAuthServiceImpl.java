package com.crazy.cc.service.auth;

import cn.hutool.core.util.ObjectUtil;
import com.crazy.cc.controller.admin.auth.vo.AuthLoginReqVO;
import com.crazy.cc.controller.admin.auth.vo.AuthLoginRespVO;
import com.crazy.cc.convert.auth.AuthConvert;
import com.crazy.cc.dal.dataobject.OAuth2.OAuth2AccessTokenDO;
import com.crazy.cc.dal.dataobject.user.AdminUserDo;
import com.crazy.cc.framework.common.enums.CommonStatusEnum;
import com.crazy.cc.framework.common.enums.LoginLogTypeEnum;
import com.crazy.cc.framework.common.util.validation.ValidationUtils;
import com.crazy.cc.framework.core.enums.UserTypeEnum;
import com.crazy.cc.service.oauth2.OAuth2TokenService;
import com.crazy.cc.service.user.AdminUserService;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Validator;

import static com.crazy.cc.framework.common.enums.ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS;
import static com.crazy.cc.framework.common.enums.ErrorCodeConstants.AUTH_LOGIN_CAPTCHA_CODE_ERROR;
import static com.crazy.cc.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private Validator validator;

    @Resource
    private CaptchaService captchaService;

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private OAuth2TokenService oAuth2TokenService;

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        // 1、校验验证码
        validateCaptcha(reqVO);
        // 2、使用账号密码登录
        AdminUserDo user = authenticate(reqVO.getUsername(), reqVO.getPassword());
        // 3、如果 socialType 非空，说明需要绑定社交用户(后期处理)
        // 4、创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }

    @Override
    public AuthLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenDO accessTokenDO = oAuth2TokenService.refreshAccessToken(refreshToken, "default");
        // 构建返回结果
        AuthLoginRespVO authLoginRespVO = new AuthLoginRespVO();
        authLoginRespVO.setAccessToken(accessTokenDO.getAccessToken());
        authLoginRespVO.setRefreshToken(accessTokenDO.getRefreshToken());
        authLoginRespVO.setUserId(accessTokenDO.getUserId());
        authLoginRespVO.setExpiresTime(accessTokenDO.getExpiresTime());
        return authLoginRespVO;
    }

    @Override
    public void logout(String token) {
        // 删除访问令牌
        // 删除刷新令牌
        oAuth2TokenService.removeAccessToken(token);
    }

    void validateCaptcha(AuthLoginReqVO reqVO) {
        // 校验验证码
        ValidationUtils.validate(validator, reqVO, AuthLoginReqVO.CodeEnableGroup.class);
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(reqVO.getCaptchaVerification());
        ResponseModel verification = captchaService.verification(captchaVO);
        if (!verification.isSuccess()) {
            // 验证码不正确
            // 失败日志 后期在写
            throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR, verification.getRepMsg());
        }
    }

    public AdminUserDo authenticate(String username, String password) {
        final LoginLogTypeEnum loginUsername = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        AdminUserDo userByUsername = adminUserService.getUserByUsername(username);
        if (userByUsername == null) {
            // 创建日志
        }
        assert userByUsername != null;
        if (!adminUserService.isPasswordMatch(password, userByUsername.getPassword())) {
             // 创建日志
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(userByUsername.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            // 创建日志
        }
        return userByUsername;
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username, LoginLogTypeEnum logType) {
        // 插入登录日志 后期处理
        // 创建访问令牌
        OAuth2AccessTokenDO accessTokenDO = oAuth2TokenService.createAccessToken(userId, getUserType().getValue(), "default", null);
        // 构建返回结果
        AuthLoginRespVO authLoginRespVO = new AuthLoginRespVO();
        authLoginRespVO.setAccessToken(accessTokenDO.getAccessToken());
        authLoginRespVO.setRefreshToken(accessTokenDO.getRefreshToken());
        authLoginRespVO.setUserId(accessTokenDO.getUserId());
        authLoginRespVO.setExpiresTime(accessTokenDO.getExpiresTime());
        return authLoginRespVO;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

}
