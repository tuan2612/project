package com.huce.project.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
@Component
public class ConfigBean {
     @Bean
    public ModelMapper init(){
        return new ModelMapper();
    }
}
