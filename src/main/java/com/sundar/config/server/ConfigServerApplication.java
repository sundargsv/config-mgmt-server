package com.sundar.config.server;

import com.sundar.config.server.common.Helper;
import com.sundar.config.server.config.GitRemoteRepositoryOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@Configuration
@EnableScheduling
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
		log.info("Configuration Management Server started ..... At={}", Helper.getLocalUtcDateTime());
	}

	/*
	* Initiating Git cloud repository clone and other configs
	 */
	@Bean(initMethod="init")
	@ConditionalOnExpression(
			"${config.enabled:true} and '${config.type}'.equals('GIT')"
	)
	public GitRemoteRepositoryOperations initGitCloneRemoteRepository() {
		return new GitRemoteRepositoryOperations();
	}

}
