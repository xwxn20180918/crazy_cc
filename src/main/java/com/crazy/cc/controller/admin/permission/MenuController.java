package com.crazy.cc.controller.admin.permission;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "管理后台 - 菜单")
@RestController
@RequestMapping("/admin/api/system/menu")
@Validated
public class MenuController {
}
