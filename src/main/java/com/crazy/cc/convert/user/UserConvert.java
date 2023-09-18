package com.crazy.cc.convert.user;

import com.crazy.cc.controller.admin.auth.vo.AuthLoginRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

//    AuthLoginRespVO convert(OAuth2AccessTokenDO bean);
}
