package com.practice.bootstrapping.configurations;

import org.springframework.beans.factory.annotation.Value;
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
public class SwaggerDocumentationConfig {

    @Value("${enable.swagger.plugin:true}")
    private boolean enableSwaggerPlugin;
  
    ApiInfo apiInfo() {

        return new ApiInfoBuilder()
            .title("Bootstrap Application")
            .description("" +
                    "This application was founded to bring all basic knowledge under one roof." +
                    "The application is simple mapping of users and vehicles, but this also has information's about bulk uploads," +
                    "complete excel processing, test cases and mocking, aop, async, exception handling, data set with command line runner" +
                    ", basically all basic knowledge about the development" +
                    "")
            .license("MIT")
            .licenseUrl("https://opensource.org/licenses/MIT")
            .version("4.6.95")
            .contact(new Contact("Anshit",
                    "https://github.com/hianshithere", ""))
            .build();
    }

    @Bean
    public Docket customImplementation() {
      return new Docket(DocumentationType.SWAGGER_2)
          .useDefaultResponseMessages(false)
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.practice.bootstrapping"))
          .paths(PathSelectors.any())
          .build()
          .enable(enableSwaggerPlugin)
          .apiInfo(apiInfo());
    }
}