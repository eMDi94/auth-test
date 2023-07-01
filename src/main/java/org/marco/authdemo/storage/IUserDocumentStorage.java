package org.marco.authdemo.storage;

import org.marco.authdemo.models.User;
import org.marco.authdemo.storage.exceptions.StorageException;
import org.springframework.web.multipart.MultipartFile;

public interface IUserDocumentStorage {
    String loadDocument(User user, MultipartFile file) throws StorageException;
}
