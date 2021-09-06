package com.sundar.config.server.service;

import com.sundar.config.server.service.model.ConfigurationResponse;

public interface ConfigManagementService {

    ConfigurationResponse loadConfig(String applicationName, String environment);
}
