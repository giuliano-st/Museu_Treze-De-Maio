package com.acervo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Todas as rotas que não são da API redirecionam para o index.html do React
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/login").setViewName("forward:/index.html");
        registry.addViewController("/register").setViewName("forward:/index.html");
        registry.addViewController("/pesquisa").setViewName("forward:/index.html");
        registry.addViewController("/cadastro").setViewName("forward:/index.html");
        registry.addViewController("/cadastro/livro").setViewName("forward:/index.html");
        registry.addViewController("/cadastro/jornal").setViewName("forward:/index.html");
        registry.addViewController("/cadastro/revista").setViewName("forward:/index.html");
        registry.addViewController("/perfil").setViewName("forward:/index.html");
        registry.addViewController("/admin").setViewName("forward:/index.html");
        registry.addViewController("/obra/{id}").setViewName("forward:/index.html");
    }
}
