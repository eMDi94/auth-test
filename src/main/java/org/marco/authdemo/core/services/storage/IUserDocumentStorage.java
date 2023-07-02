package org.marco.authdemo.core.services.storage;

import org.marco.authdemo.core.models.User;
import org.marco.authdemo.core.exceptions.StorageException;
import org.springframework.web.multipart.MultipartFile;

public interface IUserDocumentStorage {
    String loadDocument(User user, MultipartFile file) throws StorageException;
}
