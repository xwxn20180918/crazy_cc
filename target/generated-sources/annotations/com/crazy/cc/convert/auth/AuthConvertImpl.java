package com.crazy.cc.convert.auth;

import com.crazy.cc.controller.admin.auth.vo.AuthLoginRespVO;
import com.crazy.cc.dal.dataobject.OAuth2.OAuth2AccessTokenDO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-20T23:08:56+0800",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class AuthConvertImpl implements AuthConvert {

    @Override
    public AuthLoginRespVO convert(OAuth2AccessTokenDO bean) {
        if ( bean == null ) {
            return null;
        }

        AuthLoginRespVO authLoginRespVO = new AuthLoginRespVO();

        authLoginRespVO.setUserId( bean.getUserId() );
        authLoginRespVO.setAccessToken( bean.getAccessToken() );
        authLoginRespVO.setRefreshToken( bean.getRefreshToken() );
        authLoginRespVO.setExpiresTime( bean.getExpiresTime() );

        return authLoginRespVO;
    }
}
