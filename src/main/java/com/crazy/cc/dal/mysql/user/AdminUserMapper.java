package com.crazy.cc.dal.mysql.user;

import com.crazy.cc.dal.dataobject.user.AdminUserDo;
import com.crazy.cc.framework.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminUserMapper extends BaseMapperX<AdminUserDo> {

    default AdminUserDo selectByUsername(String username) {
        return  selectOne(AdminUserDo::getUsername, username);
    }
}
