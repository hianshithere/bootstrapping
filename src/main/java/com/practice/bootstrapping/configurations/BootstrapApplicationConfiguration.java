package com.practice.bootstrapping.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security")
@Getter
@Setter
public class BootstrapApplicationConfiguration {

    private String property;

    @JsonProperty("app-meta")
    private ApplicationMetadata appMetadata;

}
