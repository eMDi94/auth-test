package org.marco.authdemo.core.configs;

import io.minio.MinioClient;
import org.marco.authdemo.core.services.storage.IUserDocumentStorage;
import org.marco.authdemo.core.services.storage.UsersMinioStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Value("${storage.users.minio-bucket}")
    private String usersMinioBucket;

    @Bean
    public IUserDocumentStorage usersStorage(MinioClient minioClient) {
        return new UsersMinioStorageService(minioClient, usersMinioBucket);
    }

}
