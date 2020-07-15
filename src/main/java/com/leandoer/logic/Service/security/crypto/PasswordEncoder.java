package com.leandoer.logic.service.security.crypto;

import java.util.Optional;

public interface PasswordEncoder {
    Optional<String> encode(String data);
}
