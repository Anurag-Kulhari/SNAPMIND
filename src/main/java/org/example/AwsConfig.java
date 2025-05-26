package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

@Configuration
public class AwsConfig {
                                                                                                                                                                                                ``                  `
    @Bean
    public BedrockRuntimeClient bedrockClient() {
        return BedrockRuntimeClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("snapmind-profile")) // <-- Use your profile name here
                .region(Region.of("us-east-1")) // or whatever region you configured
                .build();
    }
}
