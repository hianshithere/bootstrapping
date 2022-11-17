package com.practice.bootstrapping.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationMetadata {

    @JsonProperty("app-name")
    public String appName;

    public String version;

}
