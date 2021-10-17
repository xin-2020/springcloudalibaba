package com.tulingxueyuan.pre;

/**
 * @Author
 * @date 2021年10月14日14:48
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 解决跨域问题
 */
@Configuration
public class CorsConfig  {
    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration conf=new CorsConfiguration();
        conf.addAllowedHeader("*");
        conf.addAllowedOrigin("*");
        conf.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**",conf);
        return new CorsWebFilter(source);
    }
}

