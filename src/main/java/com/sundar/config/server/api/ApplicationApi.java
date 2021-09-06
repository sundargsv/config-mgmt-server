package com.sundar.config.server.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.sundar.config.server.api.model.response.Project;
import com.sundar.config.server.common.Constants;
import com.sundar.config.server.exception.InternalServerError;
import com.sundar.config.server.exception.NotFound;
import com.sundar.config.server.service.ConfigManagementService;
import com.sundar.config.server.service.model.ConfigurationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

import static com.sundar.config.server.common.Constants.FILE_EXTENSION;

@RestController
@RequestMapping(path = {Constants.API_BASE_CONTEXT_PATH}, produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class ApplicationApi {

    @Value("${project.name}")
    private String name;

    @Value("${project.version}")
    private String version;

    @Autowired
    private ConfigManagementService configManagementService;

    @GetMapping(path = {"/version"})
    public Project getVersion() {
        log.info("Checking Health point...{}", "Configuration Management Server");

        return Project.builder()
                .name(name)
                .version(version)
                .build();
    }

    // get config properties by application name and env (dev/ stage/ prod)
    // TODO: Improvement config-client can be created as a client dependency that can be added to the respective application server
    @GetMapping(path = {"/{appName}"})
    public ConfigurationResponse getConfigByApplicationAndEnv(@PathVariable String appName, @RequestParam String environment) {
        log.info("Request Received to get config for AppName={} | Env={}", appName, environment);
        return configManagementService.loadConfig(appName, environment);

    }

}
