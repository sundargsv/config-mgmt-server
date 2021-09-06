package com.sundar.config.server.service;

import com.sundar.config.server.exception.NotFound;
import com.sundar.config.server.model.ConfigDataModel;
import com.sundar.config.server.model.ConfigServerDataModel;
import com.sundar.config.server.service.implementation.ConfigManagementServiceImpl;
import com.sundar.config.server.service.model.ConfigurationResponse;
import lombok.extern.slf4j.Slf4j;

import static com.sundar.config.server.common.Helper.getAsteriscs;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class ConfigManagementServiceTests {

    @Mock
    private Utils utils;

    @InjectMocks
    ConfigManagementServiceImpl configManagementService;

    @Test
    public void c1_loadConfig_local_returnSuccess() throws IOException {
        log.info("{} c1_loadConfig_local_returnSuccess START {}", getAsteriscs(), getAsteriscs());

        ReflectionTestUtils.setField(configManagementService, "configType", "LOCAL");
        ReflectionTestUtils.setField(configManagementService, "localConfigDir", "src/test/resources/storage/app1-dev.yaml");

        // prepare the yaml property response
        ConfigDataModel configDataMock = ConfigDataModel.builder()
                .storage("local")
                .server(ConfigServerDataModel.builder().port("8080").build())
                .build();
        Object propertiesMock = configDataMock;
        ConfigurationResponse expectedResponse = ConfigurationResponse.builder()
                .application("app1")
                .environment("dev")
                .properties(propertiesMock)
                .build();

        when(utils.parseConfig(anyString())).thenReturn(propertiesMock);
        when(utils.isExists(anyString())).thenReturn(Boolean.TRUE);


        ConfigurationResponse actualResponse = configManagementService.loadConfig("app1", "dev");
        assertEquals(expectedResponse.getApplication(), actualResponse.getApplication());

        log.info("{} c1_loadConfig_local_returnSuccess END {}", getAsteriscs(), getAsteriscs());
    }

    @Test(expected = NotFound.class)
    public void c1_loadConfig_local_throwsNotFound() throws IOException {
        log.info("{} c1_loadConfig_local_throwsNotFound START {}", getAsteriscs(), getAsteriscs());

        ReflectionTestUtils.setField(configManagementService, "configType", "LOCAL");
        ReflectionTestUtils.setField(configManagementService, "localConfigDir", "src/test/resources/storage/app1-dev.yaml");

        // prepare the yaml property response
        ConfigDataModel configDataMock = ConfigDataModel.builder()
                .storage("local")
                .server(ConfigServerDataModel.builder().port("8080").build())
                .build();
        Object propertiesMock = configDataMock;
        ConfigurationResponse expectedResponse = ConfigurationResponse.builder()
                .application("app1")
                .environment("dev")
                .properties(propertiesMock)
                .build();


        // mock it as file not exists
        when(utils.isExists(anyString())).thenReturn(Boolean.FALSE);


        ConfigurationResponse actualResponse = configManagementService.loadConfig("app1", "dev");
        assertEquals(expectedResponse.getApplication(), actualResponse.getApplication());

        log.info("{} c1_loadConfig_local_throwsNotFound END {}", getAsteriscs(), getAsteriscs());
    }

}
