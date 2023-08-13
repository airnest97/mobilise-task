package com.mobilise.task.services.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mobilise.task.dtos.AmazonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service(AwsS3Service.NAME)
@RequiredArgsConstructor
public class AwsS3Service implements IStorageService {

    public static final String NAME = "IAwsS3Service";

    private final AmazonS3 amazonS3;
    @Value("${SERVICE_BUCKET_NAME}")
    String userDocumentBucketName;

    @Async
    public void createBucket(String name) {
        if (!doesBucketExist(name))
            amazonS3.createBucket(name);
    }

    public void createFolder(String bucketName, String name, String access) {
        if (doesBucketExist(name) && !doesObjectExist(bucketName, name)) {
            if (!name.endsWith(FILE_SEPARATOR)) name += FILE_SEPARATOR;

            // create meta-data for your folder and set content-length to 0
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(0);

            // create empty content
            InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, name, emptyContent, metadata)
                    .withCannedAcl(CannedAccessControlList.valueOf(access));

            amazonS3.putObject(putObjectRequest);
        }
    }

    public String getUrl(String bucketName, String name) {
        return amazonS3.getUrl(bucketName, name).toString();
    }

    public boolean doesBucketExist(String name) {
        return amazonS3.doesBucketExistV2(name);
    }

    public boolean doesObjectExist(String bucketName, String name) {
        return amazonS3.doesObjectExist(bucketName, name);
    }

    public AmazonResponse uploadToBucket(InputStream inputStream, UploadObject uploadObject) throws IOException {
       return uploadToBucket(inputStream, uploadObject, CannedAccessControlList.PublicRead.name());
    }

    public AmazonResponse uploadToBucket(InputStream inputStream, UploadObject uploadObject, String access) throws IOException {
        String bucketName = uploadObject.getBucketName();
        String name = uploadObject.getName();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);
        objectMetadata.setContentLength(inputStream.available());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, name, inputStream, objectMetadata);
        putObjectRequest.setCannedAcl(CannedAccessControlList.valueOf(access));
        amazonS3.putObject(putObjectRequest);
        String url = getUrl(bucketName, name);
        return AmazonResponse.builder().url(url).fileName(name).build();
    }
    public void deleteFile(String name){
        amazonS3.deleteObject(userDocumentBucketName, name);
    }

}
