package com.mahaexam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;

@Configuration
@ConditionalOnProperty(name = "spring.cloud.aws.sqs.enabled", havingValue = "true")
@Slf4j
public class CustomSqsConfig {

    @Value("${aws.accessKeyId:}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey:}")
    private String secretAccessKey;

    @Value("${spring.cloud.aws.region.static:ap-south-1}")
    private String region;

    @Bean
    @Primary
    public SqsAsyncClient sqsAsyncClient() {
        log.info("Creating custom SqsAsyncClient with region: {}", region);
        log.info("Access key provided: {}", accessKeyId != null && !accessKeyId.isEmpty() ? "YES" : "NO");

        AwsCredentialsProvider credentialsProvider;

        if (accessKeyId != null && !accessKeyId.isEmpty()
            && secretAccessKey != null && !secretAccessKey.isEmpty()) {
            log.info("Using static credentials provider");
            credentialsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKeyId, secretAccessKey)
            );
        } else {
            log.info("Using default credentials provider chain");
            credentialsProvider = DefaultCredentialsProvider.create();
        }

        return SqsAsyncClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider)
                .build();
    }

    @Bean
    @Primary
    public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {
        return SqsMessageListenerContainerFactory
                .builder()
                .configure(options -> options
                        .pollTimeout(java.time.Duration.ofSeconds(10))
                )
                .sqsAsyncClient(sqsAsyncClient)
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient) {
        return SqsTemplate.builder()
                .sqsAsyncClient(sqsAsyncClient)
                .build();
    }
}
