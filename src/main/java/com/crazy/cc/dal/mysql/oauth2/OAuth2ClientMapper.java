package com.crazy.cc.dal.mysql.oauth2;

import com.crazy.cc.framework.common.pojo.PageResult;
import com.crazy.cc.framework.core.mapper.BaseMapperX;
import com.crazy.cc.framework.core.query.LambdaQueryWrapperX;
import com.crazy.cc.service.oauth2.dto.OAuth2ClientDO;
import com.crazy.cc.service.oauth2.dto.OAuth2ClientPageReqVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OAuth2ClientMapper extends BaseMapperX<OAuth2ClientDO> {
    default PageResult<OAuth2ClientDO> selectPage(OAuth2ClientPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OAuth2ClientDO>()
                .likeIfPresent(OAuth2ClientDO::getName, reqVO.getName())
                .eqIfPresent(OAuth2ClientDO::getStatus, reqVO.getStatus())
                .orderByDesc(OAuth2ClientDO::getId));
    }

    default OAuth2ClientDO selectByClientId(String clientId) {
        return selectOne(OAuth2ClientDO::getClientId, clientId);
    }
}
