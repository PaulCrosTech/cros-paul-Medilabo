package com.medilabo.ms_config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
@EnableConfigServer
public class MsConfigApplication {

    public static void main(String[] args) {

        dockerModeLoadGitCredential();

        ConfigurableApplicationContext context = SpringApplication.run(MsConfigApplication.class, args);

        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        if (Arrays.asList(activeProfiles).contains("local")) {
            log.error("=======================================================================");
            log.error("<!> Please consult README.md : in 'local' mode ms-config is useless <!>");
            log.error("=======================================================================");
            System.exit(0);
        }

    }


    /**
     * Try to load Git credentials ('docker' mode)
     */
    private static void dockerModeLoadGitCredential()
    {
        try {

            String secretFilePath = System.getenv("GIT_CREDENTIALS");
            if (secretFilePath == null || secretFilePath.isEmpty()) {
                return;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            File secretFile = new File(secretFilePath);
            if (!secretFile.exists()) {
                throw new RuntimeException("JSON file not found");
            }
            if (!secretFile.canRead()) {
                throw new RuntimeException("JSON file is no readable");
            }

            JsonNode secrets = objectMapper.readTree(secretFile);


            String uri = secrets.get("MS_CONFIG_GIT_URI").asText();
            String username = secrets.get("MS_CONFIG_GIT_USERNAME").asText();
            String pat = secrets.get("MS_CONFIG_GIT_PAT").asText();

            if (uri == null || username == null || pat == null) {
                throw new RuntimeException("One or more secrets are missing in the JSON file");
            }

            System.setProperty("spring.cloud.config.server.git.uri", uri);
            System.setProperty("spring.cloud.config.server.git.username", username);
            System.setProperty("spring.cloud.config.server.git.password", pat);

        } catch (Exception e) {
            log.error("Please consult README.md to set Git credentials : {}", e.getMessage());
            System.exit(0);
        }
    }

}
