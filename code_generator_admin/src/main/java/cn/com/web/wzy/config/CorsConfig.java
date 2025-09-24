package cn.com.web.wzy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域处理
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许来自 localhost:5173 的跨域请求
        config.addAllowedOriginPattern("*");
        config.setAllowCredentials(true); // 允许携带凭证（如 Cookie）
        config.addAllowedMethod("*");     // 允许所有 HTTP 方法
        config.addAllowedHeader("*");     // 允许所有请求头

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 对所有接口都有效

        return new CorsFilter(source);
    }
}
