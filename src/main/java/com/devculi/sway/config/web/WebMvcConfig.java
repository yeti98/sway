package com.devculi.sway.config.web;

import com.devculi.sway.constants.ConfigResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName(ConfigResolver.WELCOME_PAGE_TEMPLATE_NAME);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/public/image/**")
        .addResourceLocations("classpath:/static/uploadmedia/");
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
  }
}
