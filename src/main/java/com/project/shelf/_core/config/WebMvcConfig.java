package com.project.shelf._core.config;


import com.project.shelf._core.interceptor.AppInterceptor;
import com.project.shelf._core.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 로그인 인터셉터
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/api/**")
//                .excludePathPatterns();
        registry.addInterceptor(new AppInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns();
        registry.addInterceptor(new AppInterceptor())
                .addPathPatterns("/app/**")
                .excludePathPatterns();

    }

    //외부이미지 경로설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:./image/")
                .setCachePeriod(60 * 60) // 초 단위 => 한시간
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);

        // CORS 설정 추가
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://example.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}