package com.crazy.cc.controller.admin.user;

import com.crazy.cc.framework.common.pojo.CommonResult;
import com.crazy.cc.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static com.crazy.cc.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 用户")
@RestController
@RequestMapping("/system/user")
@Validated
public class UserController {

    @GetMapping("/test")
    @PermitAll
    public CommonResult<Long> createUser() {
        return success(11222L);
    }
}
