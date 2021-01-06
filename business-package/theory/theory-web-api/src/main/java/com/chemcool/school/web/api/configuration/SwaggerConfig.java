package com.chemcool.school.web.api.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;


@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerConfigProperties.class)
@RequiredArgsConstructor
public class SwaggerConfig {

    private final SwaggerConfigProperties swaggerConfigProperties;

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerConfigProperties.getHost())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chemcool.school"))
                .build();
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/*"))
                .build();
    }

    private List<SecurityReference> defaultAuth(){
        SecurityReference securityReference = new SecurityReference("JWT", new AuthorizationScope[]{
                new AuthorizationScope("global", "accessEverything")
        });

        return Collections.singletonList(securityReference);
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
}

