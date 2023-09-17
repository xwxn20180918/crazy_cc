package com.crazy.cc.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {
    @Override
    public Long test() {
        return 111L;
    }
}
