package com.belhard.bookstore;

import com.belhard.bookstore.interceptors.OrderAccessInterceptor;
import com.belhard.bookstore.interceptors.UsersAccessInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Bean
    public UsersAccessInterceptor usersAccessInterceptor() {
        return new UsersAccessInterceptor();
    }

    @Bean
    public OrderAccessInterceptor orderAccessInterceptor() {
        return new OrderAccessInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(usersAccessInterceptor())
                .addPathPatterns("/users/**")
                .excludePathPatterns("/users/create", "/users/login");
        registry.addInterceptor(orderAccessInterceptor())
                .addPathPatterns("/orders/**");
    }

}
