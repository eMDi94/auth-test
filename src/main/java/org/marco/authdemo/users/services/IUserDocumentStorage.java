package org.marco.authdemo.users.services;

import org.marco.authdemo.users.models.User;
import org.marco.authdemo.users.exceptions.UserStorageException;
import org.springframework.web.multipart.MultipartFile;

public interface IUserDocumentStorage {
    String loadDocument(User user, MultipartFile file) throws UserStorageException;
}
