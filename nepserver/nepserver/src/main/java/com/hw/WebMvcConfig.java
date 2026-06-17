package com.hw;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 实现WebMvcConfigurer接口：自定义SpringMVC相关扩展配置，此处用来配置全局跨域CORS规则
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 重写addCorsMappings方法：配置全局CORS跨域资源共享规则
     * 跨域产生场景：浏览器同源策略限制，协议、域名、端口任意一个不同即为跨域
     * 前端(JS/Vue/axios)访问后端接口出现Access-Control-Allow-Origin报错时，通过该配置解决
     * @param registry 跨域注册器，用于链式配置跨域参数
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // addMapping("/**")：配置需要开启跨域的接口路径，/**代表项目中所有接口全部启用跨域
        registry.addMapping("/**")
                // allowedOrigins("*")：允许所有来源域名访问，*为通配符，所有前端域名/IP都可跨域请求
                // 生产环境建议替换为指定域名，例如：allowedOrigins("http://localhost:8080","https://xxx.com")
                .allowedOrigins("*")
                // allowedMethods("*")：允许所有HTTP请求方式，GET/POST/PUT/DELETE/OPTIONS等全部放行
                // 如需限制请求方式可指定：allowedMethods("GET","POST")
                .allowedMethods("*")
                // allowedHeaders("*")：允许前端请求携带任意自定义请求头，token、Authorization等自定义头部放行
                .allowedHeaders("*")
                .maxAge(36000);
    }
}