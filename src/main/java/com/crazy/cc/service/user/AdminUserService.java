package com.crazy.cc.service.user;

import com.crazy.cc.dal.dataobject.user.AdminUserDo;

public interface AdminUserService {

    AdminUserDo getUserByUsername(String username);


    boolean isPasswordMatch(String rawPassword, String encodedPassword);
}
