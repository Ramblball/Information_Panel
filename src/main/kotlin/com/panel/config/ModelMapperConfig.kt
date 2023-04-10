package com.panel.config

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@Component
class ModelMapperConfig {
    @Bean
    fun modelMapper(): ModelMapper {
        return ModelMapper()
    }
}