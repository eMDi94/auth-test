package org.marco.authdemo.system.gdpr.attributes;

import jakarta.persistence.AttributeConverter;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;


public abstract class AbstractAttributeConverter<T> implements AttributeConverter<T, String> {

    private static final String DATABASE_ENCRYPTION_PASSWORD;
    private static final String SALT = KeyGenerators.string().generateKey();
    static {
        DATABASE_ENCRYPTION_PASSWORD = System.getenv("DATABASE_ENCRYPTION_PASSWORD");
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        TextEncryptor encryptor = getEncryptor();
        if (encryptor != null && attribute != null)
            return encrypt(encryptor, attribute);
        return entityAttributeToString(attribute);
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        TextEncryptor encryptor = getEncryptor();
        if (encryptor != null && dbData != null)
            return decrypt(encryptor, dbData);
        return stringToEntityAttribute(dbData);
    }

    private TextEncryptor getEncryptor() {
        return DATABASE_ENCRYPTION_PASSWORD != null ?
                Encryptors.text(DATABASE_ENCRYPTION_PASSWORD, SALT) : null;
    }

    abstract protected T stringToEntityAttribute(String data);

    abstract protected String entityAttributeToString(T attribute);

    private String encrypt(TextEncryptor encryptor, T attribute) {
        String attributeString = entityAttributeToString(attribute);
        return encryptor.encrypt(attributeString);
    }

    private T decrypt(
            TextEncryptor encryptor, String attributeString) {
        String decryptedAttributeString = encryptor.decrypt(attributeString);
        return stringToEntityAttribute(decryptedAttributeString);
    }
}
