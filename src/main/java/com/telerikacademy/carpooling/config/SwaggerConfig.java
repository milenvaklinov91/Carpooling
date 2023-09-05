package com.telerikacademy.carpooling.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.telerikacademy.carpooling"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Carpooling API")
                .description("API documentation for the Carpooling application")
                .version("1.0.0")
                .contact(new Contact("Milen Vaklinov, Leda Yovkova, Kaloyan Stanev", "https://gitlab.com/ledkaa/carpooling", "ledkaa@gmail.com, milenvaklinov@gmail.com, kokostanev20@gmail.com"))
                .build();
    }
}
