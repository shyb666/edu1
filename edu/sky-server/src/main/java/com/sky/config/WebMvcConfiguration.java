package com.sky.config;

import com.sky.handler.MyWsHandler;
import com.sky.interceptor.JwtTokenUserInterceptor;
import com.sky.interceptor.MyWsInterceptor;
import com.sky.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;


    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");

       registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**")
                .excludePathPatterns("/student/**")
                .excludePathPatterns("/teacher/**");

    }

    /**
     * 通过knife4j生成接口文档
     * @return
     */
    @Bean
    public Docket docket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("苍穹外卖项目接口文档")
                .version("2.0")
                .description("苍穹外卖项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sky.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 设置静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    /**
     * 扩展mvc框架的消息转换器，转换时间格式,对后端返回给前端的数据进行统一的处理
     * @param  converters
     */

    @Override
protected void extendMessageConverters(List<HttpMessageConverter<?>> converters)
{

    //创建一个消息转换器
    MappingJackson2HttpMessageConverter converter=new MappingJackson2HttpMessageConverter();
    //设置一个对象转换器，将java对象转化为json数据
    converter.setObjectMapper(new JacksonObjectMapper());
    //消息转换器加入容器中
    converters.add(0,converter);

}

    // CORS过滤器
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173"); // 允许所有源，注意：在生产环境中使用 "*" 可能不安全
        config.setAllowCredentials(true);
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedHeader("*");
        config.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }


}
