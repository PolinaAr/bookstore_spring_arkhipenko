package com.belhard.bookstore;

import com.belhard.bookstore.controller.interceptors.OrderAccessInterceptor;
import com.belhard.bookstore.controller.interceptors.UsersAccessInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BookstoreApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

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
                .excludePathPatterns("/users/create", "/users/login", "/users/logout");
        registry.addInterceptor(orderAccessInterceptor())
                .addPathPatterns("/orders/**")
                .excludePathPatterns("/orders/user/**");
    }

}
