package com.oris_spring.infrastucture;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

public class AppConfig {
    ViewResolver resolver = new FreeMarkerViewResolver();

}
