package com.sundar.config.server.api;

import com.sundar.config.server.api.model.response.Project;
import com.sundar.config.server.model.ConfigDataModel;
import com.sundar.config.server.model.ConfigServerDataModel;
import com.sundar.config.server.service.ConfigManagementService;
import com.sundar.config.server.service.model.ConfigurationResponse;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import static com.sundar.config.server.common.Helper.getAsteriscs;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ApplicationApiTests {

    // bind the above RANDOM_PORT
    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @MockBean
    ConfigManagementService configManagementService;

    @Test
    public void getVersion_returnSuccess() throws Exception {

        ResponseEntity<Project> response = restTemplate.getForEntity(
                new URL("http://localhost:" + port + "/config/version").toString(), Project.class);
        assertEquals("config-server-test", response.getBody().getName());
        assertEquals("1.0.0", response.getBody().getVersion());

    }

    @Test
    public void getConfig_returnSuccess() throws Exception {
        log.info("{} getConfig_returnSuccess START {}", getAsteriscs(), getAsteriscs());

        String URL = "http://localhost:" + port + "/config/app3";

        ConfigurationResponse expectedResponse = prepareConfigResponse();

                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("environment", "dev");

        when(configManagementService.loadConfig(anyString(), anyString())).thenReturn(expectedResponse);

        ResponseEntity<ConfigurationResponse> actualResponse = restTemplate.getForEntity(
                builder.toUriString(), ConfigurationResponse.class);
        assertEquals(expectedResponse.getApplication(), actualResponse.getBody().getApplication());
        assertNotNull(actualResponse.getBody().getProperties());

        log.info("{} getConfig_returnSuccess END {}", getAsteriscs(), getAsteriscs());

    }

    @Test
    public void getConfig_returnNull() throws Exception {
        log.info("{} getConfig_returnNull START {}", getAsteriscs(), getAsteriscs());

        String URL = "http://localhost:" + port + "/config/app3";

        ConfigurationResponse expectedResponse = prepareConfigResponse();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("environment", "dev");


        when(configManagementService.loadConfig(anyString(), anyString())).thenReturn(null);

        ResponseEntity<ConfigurationResponse> actualResponse = restTemplate.getForEntity(
                builder.toUriString(), ConfigurationResponse.class);

        assertNull(actualResponse.getBody());
        log.info("{} getConfig_returnNull END {}", getAsteriscs(), getAsteriscs());
    }

    private ConfigurationResponse prepareConfigResponse(){

        ConfigDataModel configDataMock = ConfigDataModel.builder()
                .storage("local")
                .server(ConfigServerDataModel.builder().port("8080").build())
                .build();
        Object propertiesMock = configDataMock;

        return ConfigurationResponse.builder()
                .application("app3")
                .environment("dev")
                .properties(propertiesMock)
                .build();
    }
}
