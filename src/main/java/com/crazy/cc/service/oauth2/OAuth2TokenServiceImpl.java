package com.crazy.cc.service.oauth2;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.crazy.cc.dal.dataobject.OAuth2.OAuth2AccessTokenDO;
import com.crazy.cc.dal.mysql.oauth2.OAuth2AccessTokenMapper;
import com.crazy.cc.dal.mysql.oauth2.OAuth2RefreshTokenMapper;
import com.crazy.cc.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.crazy.cc.framework.common.util.date.DateUtils;
import com.crazy.cc.service.oauth2.dto.OAuth2ClientDO;
import com.crazy.cc.service.oauth2.dto.OAuth2RefreshTokenDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.crazy.cc.framework.common.exception.util.ServiceExceptionUtil.exception0;
import static com.crazy.cc.framework.common.util.collection.CollectionUtils.convertSet;

@Service
public class OAuth2TokenServiceImpl implements OAuth2TokenService {

    @Resource
    private Oauth2ClientService oauth2ClientService;

    @Resource
    private OAuth2RefreshTokenMapper oAuth2RefreshTokenMapper;

    @Resource
    private OAuth2AccessTokenMapper oAuth2AccessTokenMapper;

    @Override
    public OAuth2AccessTokenDO createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes) {
        OAuth2ClientDO oAuth2ClientDO = oauth2ClientService.validOAuthClientFromCache(clientId);
        // 刷新创建令牌
        OAuth2RefreshTokenDO refreshTokenDO = createOAuth2RefreshToken(userId, userType, oAuth2ClientDO, scopes);

        return createOAuth2AccessToken(refreshTokenDO, oAuth2ClientDO);
    }

    @Override
    public OAuth2AccessTokenDO refreshAccessToken(String refreshToken, String clientId) {
        OAuth2RefreshTokenDO refreshTokenDO = oAuth2RefreshTokenMapper.selectByRefreshToken(refreshToken);
        if (refreshTokenDO == null) {
            throw exception0(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "无效的刷新令牌");
        }
        // 校验 Client 匹配
        OAuth2ClientDO clientDO = oauth2ClientService.validOAuthClientFromCache(clientId);
        if (ObjectUtil.notEqual(clientId, refreshTokenDO.getClientId())) {
            throw exception0(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "刷新令牌的客户端编号不正确");
        }
        // 移除相关的访问令牌
        List<OAuth2AccessTokenDO> accessTokenDOs = oAuth2AccessTokenMapper.selectListByRefreshToken(refreshToken);
        if (CollectionUtil.isNotEmpty(accessTokenDOs)) {
            oAuth2AccessTokenMapper.deleteBatchIds(convertSet(accessTokenDOs, OAuth2AccessTokenDO::getId));
        }
        // 已过期的情况下，删除刷新令牌
        if (DateUtils.isExpired(refreshTokenDO.getExpiresTime())) {
            oAuth2RefreshTokenMapper.deleteById(refreshTokenDO.getId());
            throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "刷新令牌已过期");
        }
        return createOAuth2AccessToken(refreshTokenDO, clientDO);
    }

    private OAuth2RefreshTokenDO createOAuth2RefreshToken(Long userId, Integer userType, OAuth2ClientDO clientDO, List<String> scopes) {
        OAuth2RefreshTokenDO oAuth2RefreshTokenDO = new OAuth2RefreshTokenDO();
        oAuth2RefreshTokenDO.setRefreshToken(generateRefreshToken());
        oAuth2RefreshTokenDO.setUserId(userId);
        oAuth2RefreshTokenDO.setUserType(userType);
        oAuth2RefreshTokenDO.setClientId(clientDO.getClientId());
        oAuth2RefreshTokenDO.setScopes(scopes);
        oAuth2RefreshTokenDO.setExpiresTime(LocalDateTime.now().plusSeconds(clientDO.getRefreshTokenValiditySeconds()));
        oAuth2RefreshTokenMapper.insert(oAuth2RefreshTokenDO);
        return oAuth2RefreshTokenDO;
    }
    private OAuth2AccessTokenDO createOAuth2AccessToken(OAuth2RefreshTokenDO refreshTokenDO, OAuth2ClientDO clientDO) {
        OAuth2AccessTokenDO oAuth2AccessTokenDO = new OAuth2AccessTokenDO();
        oAuth2AccessTokenDO.setAccessToken(generateAccessToken());
        oAuth2AccessTokenDO.setUserId(refreshTokenDO.getUserId());
        oAuth2AccessTokenDO.setUserType(refreshTokenDO.getUserType());
        oAuth2AccessTokenDO.setClientId(clientDO.getClientId());
        oAuth2AccessTokenDO.setScopes(refreshTokenDO.getScopes());
        oAuth2AccessTokenDO.setRefreshToken(refreshTokenDO.getRefreshToken());
        oAuth2AccessTokenDO.setExpiresTime(LocalDateTime.now().plusSeconds(clientDO.getAccessTokenValiditySeconds()));
        oAuth2AccessTokenMapper.insert(oAuth2AccessTokenDO);
        // 记录到 Redis 中
//        oauth2AccessTokenRedisDAO.set(accessTokenDO); //
        return oAuth2AccessTokenDO;
    }

    private static String generateAccessToken() {
        return IdUtil.fastSimpleUUID();
    }


    private static String generateRefreshToken() {
        return IdUtil.fastSimpleUUID();
    }
}
