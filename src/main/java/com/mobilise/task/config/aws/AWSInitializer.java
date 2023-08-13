package com.mobilise.task.config.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("'${AWS_ACCESS_KEY_ID:}' != '' && '${AWS_SECRET_ACCESS_KEY:}' != ''")
public class AWSInitializer {

    @Value("${aws.access_key_id:${AWS_ACCESS_KEY_ID}}")
    private String awsAccessKey;

    @Value("${aws.secret_access_key:${AWS_SECRET_ACCESS_KEY}}")
    private String awsSecretKey;

    @Bean
    public AWSCredentials getAwsCredentials() {
        return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
    }

    @Bean
    public AWSStaticCredentialsProvider getAwsCredentialProvider(AWSCredentials credentials) {
        return new AWSStaticCredentialsProvider(credentials);
    }
}
