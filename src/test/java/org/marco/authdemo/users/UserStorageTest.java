package org.marco.authdemo.users;

import org.junit.jupiter.api.Test;
import org.marco.authdemo.AuthdemoApplication;
import org.marco.authdemo.users.exceptions.UserStorageException;
import org.marco.authdemo.users.models.User;
import org.marco.authdemo.users.repositories.UserRepository;
import org.marco.authdemo.users.services.IUserDocumentStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SpringBootTest(classes = AuthdemoApplication.class)
public class UserStorageTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IUserDocumentStorage userDocumentStorage;

    @Test
    void storeDocument() throws IOException, UserStorageException {
        User user = userRepository.findAll(Pageable.ofSize(1)).toList().get(0);

        MultipartFile mockFile = new MockMultipartFile("test.pdf",
                "test.pdf",
                "application/pdf",
                getClass().getResourceAsStream("MarcoDalaiCVEng.pdf"));
        userDocumentStorage.loadDocument(user, mockFile);
    }

}
