package com.sundar.config.server.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
@Slf4j
public class Helper {

    public static LocalDateTime getLocalUtcDateTime() {
        return LocalDateTime.now(Clock.systemUTC());
    }

    public static ZonedDateTime getUtcZoneDateTime(){
        return ZonedDateTime.now(Clock.systemUTC());
    }

    public static String stringify(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

    public static String getRandomUUID(){
        return UUID.randomUUID().toString();
    }

    public static Repository openJGitCookbookRepository(File getDir) throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        return builder
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir(getDir) // scan up the file system tree
                .build();
    }

    public static Object getAsteriscs(){
        return "*********************************************";
    }

}
