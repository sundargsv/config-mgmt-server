package com.sundar.config.server.config;

import com.sundar.config.server.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ConfigPolling {

    @Value("${config.type}")
    private String configType;

    @Autowired
    private GitRemoteRepositoryOperations gitRemoteRepositoryOperations;

    // TODO: 05/09/21 to add conditionOnProperty to schedule git polling if config.type = GIT
    // TODO: Improvement This process can be leveraged. If HEAD version/ checksum changed, we can be post a WebHook to config-client (which was added to the application)
    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
    public void scheduleFixedGitCloudConfigFetch() throws IOException, GitAPIException {
        //fetch latest revision from remote to local copy

        if (configType.equalsIgnoreCase("GIT"))
            gitRemoteRepositoryOperations.fetchLatestVersion(Constants.GIT_LOCAL_DIR);
    }
}
