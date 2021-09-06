package com.sundar.config.server.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfigDataModel {

    private String storage;
    private ConfigServerDataModel server;
}
