package com.mobilise.task.services.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3ClientConfig {

    @Value("${aws.region:${AWS_REGION}}")
    private String AWS_REGION;

    @Bean
    @ConditionalOnMissingBean(AmazonS3.class)
    public AmazonS3 getAwsS3Client(AWSStaticCredentialsProvider awsStaticCredentialsProvider) {
        return AmazonS3ClientBuilder.standard().withCredentials(awsStaticCredentialsProvider)
                .withRegion(AWS_REGION).build();
    }

    @Bean
    public AmazonS3 getAwsS3Client() {
        return AmazonS3ClientBuilder.standard().withRegion(AWS_REGION).build();
    }
}
