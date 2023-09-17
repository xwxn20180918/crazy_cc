package com.crazy.cc.api.user;

import com.crazy.cc.api.user.dto.AdminUserRespDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AdminUserApiImpl implements AdminUserApi{
    @Override
    public AdminUserRespDTO getUser(Long id) {
        return null;
    }

    @Override
    public List<AdminUserRespDTO> getUserList(Collection<Long> ids) {
        return null;
    }

    @Override
    public List<AdminUserRespDTO> getUserListByDeptIds(Collection<Long> deptIds) {
        return null;
    }

    @Override
    public List<AdminUserRespDTO> getUserListByPostIds(Collection<Long> postIds) {
        return null;
    }

    @Override
    public void validateUserList(Collection<Long> ids) {

    }
}
