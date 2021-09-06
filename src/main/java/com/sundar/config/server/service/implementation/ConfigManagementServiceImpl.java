package com.sundar.config.server.service.implementation;

import com.sundar.config.server.common.Constants;
import com.sundar.config.server.exception.InternalServerError;
import com.sundar.config.server.exception.NotFound;
import com.sundar.config.server.service.ConfigManagementService;
import com.sundar.config.server.service.Utils;
import com.sundar.config.server.service.model.ConfigurationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.sundar.config.server.common.Constants.FILE_EXTENSION;

@Service
@Slf4j
public class ConfigManagementServiceImpl implements ConfigManagementService {

    @Value("${config.type}")
    private String configType;

    @Value("${config.local.path}")
    private String localConfigDir;

    @Autowired
    private Utils utils;

    @Override
    public ConfigurationResponse loadConfig(String applicationName, String environment) {

        String path;

        try {

            //1. check for config type (GIT| LOCAL| AWS_S3| GCP_STORAGE) and manipulate the file path to read the property data
            switch (configType){
                case "GIT":
                    path = String.format("%s/%s-%s%s", Constants.GIT_LOCAL_DIR.toString().replace("/.git", ""), applicationName, environment, FILE_EXTENSION);
                    break;
                case "AWS_S3" :
                case "GCP_STORAGE":
                default: //local
                    path = String.format("%s/%s-%s%s", localConfigDir, applicationName, environment, FILE_EXTENSION);
            }

            //2. check if file not exists for the given criteria and throw not found error with 404 HttpStatus
            if (!utils.isExists(path))
                throw new NotFound("App Config properties/ file is not found for the given app name and environment.");

            // log.info("Path {}", path);

            //3. read the yaml formatted application config property file and convert it to generic Object class for simplicity
            Object properties = utils.parseConfig(path);

            //4. prepare response and return
            return ConfigurationResponse.builder()
                    .application(applicationName)
                    .environment(environment)
                    .properties(properties)
                    .build();
        } catch (IOException e) {
            log.error("Error in retrieving config properties for the given ApplicationName={} | Environment={} | Message={}", applicationName, environment, e.getLocalizedMessage());
            throw new InternalServerError("Error in fetching service config property data", e.getCause());
        }
    }
}
