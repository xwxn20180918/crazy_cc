package com.crazy.cc.convert.auth;

import com.crazy.cc.controller.admin.auth.vo.AuthLoginRespVO;
import com.crazy.cc.dal.dataobject.OAuth2.OAuth2AccessTokenDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthConvert {
    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);
    AuthLoginRespVO convert(OAuth2AccessTokenDO bean);
}
