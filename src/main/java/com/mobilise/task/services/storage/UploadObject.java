package com.mobilise.task.services.storage;

import com.google.common.io.Files;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Data
public class UploadObject {
    private String bucketName;
    private String name;

    public void setName(String name) {
        String nameWithoutExtension = Files.getNameWithoutExtension(name);
        String extension = Files.getFileExtension(name);
        this.name = nameWithoutExtension + "_" + RandomStringUtils.randomAlphanumeric(10) + "." + extension;
    }

    public void setName(String preName, MultipartFile multipartFile) {
        String extension = Files.getFileExtension(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        this.name = preName + "_" + RandomStringUtils.randomAlphanumeric(4) + "." + extension;
    }
}
