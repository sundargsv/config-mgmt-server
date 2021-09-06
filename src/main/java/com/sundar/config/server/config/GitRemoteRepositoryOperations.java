package com.sundar.config.server.config;

import com.sundar.config.server.common.Constants;
import com.sundar.config.server.common.Helper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class GitRemoteRepositoryOperations {

    @Value("${config.git.url}")
    private String remoteUrl;

    @Value("${config.local.copy.basePath}")
    private String localCopyBasePath;

    public void init() throws IOException, GitAPIException {
        log.info("Initializing config manager to read config from Git Cloud Remote={}", remoteUrl);

        File localPath = File.createTempFile("TestGitRepository", "test", new File(localCopyBasePath));
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }

        log.info("Cloning from Remote={} | to={}", remoteUrl, localPath);
        try (Git result = Git.cloneRepository()
                .setURI(remoteUrl)
                .setDirectory(localPath)
                .setProgressMonitor(new SimpleProgressMonitor())
                .call()) {
            // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
            Constants.GIT_LOCAL_DIR = result.getRepository().getDirectory();
            log.info("Having repository: Directory={}", Constants.GIT_LOCAL_DIR);
        }

        // FileUtils.deleteDirectory(localPath);
        //fetch latest revision from remote to local copy
        fetchLatestVersion(Constants.GIT_LOCAL_DIR);
    }

    public void fetchLatestVersion(File localDirectory) throws IOException, GitAPIException {
        log.info("Pulling the data for the existing repo... Directory={}", Constants.GIT_LOCAL_DIR);

        try (Repository repository = Helper.openJGitCookbookRepository(Constants.GIT_LOCAL_DIR)) {
            try (Git git = new Git(repository)) {
                PullResult result = git.pull().call();
                log.info("Pulled latest version Status={}", result.isSuccessful());
            }
        }
    }

    private static class SimpleProgressMonitor implements ProgressMonitor {
        @Override
        public void start(int totalTasks) {
            log.info("Starting work on TotalTasks={} tasks", totalTasks);
        }

        @Override
        public void beginTask(String title, int totalWork) {
            log.info("Start Title={} | TotalWork={}", title, totalWork);
        }

        @Override
        public void update(int completed) {
            System.out.print(completed + "-");
        }

        @Override
        public void endTask() {
            System.out.println("Done");
        }

        @Override
        public boolean isCancelled() {
            return false;
        }
    }
}

