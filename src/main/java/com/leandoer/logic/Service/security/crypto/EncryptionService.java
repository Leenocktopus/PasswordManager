package com.leandoer.logic.service.security.crypto;

import java.util.Optional;

public interface EncryptionService {
    Optional<String> encrypt(String data, String key);

    Optional<String> decrypt(String data, String key);
}
