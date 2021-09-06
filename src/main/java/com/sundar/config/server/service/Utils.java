package com.sundar.config.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class Utils {

    private ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    /**
     * helper to parse the yaml formatted config properties for the given file path
     * */
    public Object parseConfig(String path) throws IOException {
        return MAPPER.readValue(new File(path), Object.class);
    }

    /**
     * helper to check if file exists for the given file path
     * */
    public Boolean isExists(String path) {
        return new File(path).exists();
    }
}
