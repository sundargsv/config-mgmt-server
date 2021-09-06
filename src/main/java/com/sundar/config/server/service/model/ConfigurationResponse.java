package com.sundar.config.server.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationResponse {

    private String application;
    private String environment;

    // TODO: 05/09/21 properties can be formatted as a key/ value pair mapper
    private Object properties;
}
