package com.crazy.cc.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.crazy.cc.framework.core.handler.DefaultDBFieldHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@MapperScan
public class CCMybatisAutoConfiguration {

    @Bean
    public MetaObjectHandler defaultMetaObjectHandler(){
        return new DefaultDBFieldHandler(); // 自动填充参数类
    }
}
