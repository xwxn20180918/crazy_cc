package com.crazy.cc.service.user;

import com.crazy.cc.dal.dataobject.user.AdminUserDo;
import com.crazy.cc.dal.mysql.user.AdminUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUserDo getUserByUsername(String username) {
        return adminUserMapper.selectByUsername(username);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
