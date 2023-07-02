package org.marco.authdemo.users.services;

import io.minio.*;
import io.minio.errors.*;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.marco.authdemo.users.models.User;
import org.marco.authdemo.users.exceptions.UserStorageException;
import org.springframework.context.annotation.Profile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Profile("users-prod")
@RequiredArgsConstructor
public class UsersMinioStorageService implements IUserDocumentStorage {

    private final MinioClient minioClient;
    private final String usersBucket;

    @PostConstruct
    protected void postConstruct() {
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder()
                .bucket(usersBucket)
                .build();

        try {
            if (!minioClient.bucketExists(bucketExistsArgs)) {
                MakeBucketArgs args = MakeBucketArgs.builder().bucket(usersBucket).build();
                minioClient.makeBucket(args);
            }
        } catch (ErrorResponseException | InsufficientDataException | InternalException
                 | InvalidKeyException | InvalidResponseException | IOException
                 | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(new UserStorageException(e));
        }
    }

    @Override
    public String loadDocument(@NonNull User user, @NonNull MultipartFile file) throws UserStorageException {
        Optional<File> tmpFile = Optional.empty();
        try {
            tmpFile = Optional.of(Files.createTempFile(null, null).getFileName().toFile());
            try (OutputStream os = new FileOutputStream(tmpFile.get())) {
                os.write(file.getBytes());
            }

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = user.getFiscalCode() + "/" + UUID.randomUUID() + "." + extension;
            UploadObjectArgs args = UploadObjectArgs.builder()
                    .bucket(usersBucket)
                    .object(fileName)
                    .filename(tmpFile.get().getAbsolutePath())
                    .contentType(file.getContentType())
                    .build();

            ObjectWriteResponse response = minioClient.uploadObject(args);
            return response.object();
        } catch (ErrorResponseException | InsufficientDataException | InternalException
                 | InvalidKeyException | InvalidResponseException | IOException
                 | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            log.error(e.getMessage(), e);
            throw new UserStorageException(e);
        } finally {
            tmpFile.map(File::delete);
        }
    }

}
