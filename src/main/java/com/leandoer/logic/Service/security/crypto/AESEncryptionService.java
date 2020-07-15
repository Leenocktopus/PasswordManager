package com.leandoer.logic.service.security.crypto;


import org.springframework.stereotype.Service;

import javax.crypto.Cipher;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Service
public class AESEncryptionService implements EncryptionService {
    public static final IvParameterSpec parameterSpec =
            new IvParameterSpec(new byte[]{ 2, 1, 0, 3, 1, 0, 4, 7, 0, 0, 0, 1, 4, 0, 0, 0});

    public SecretKeySpec getKey(String originalKey){

        SecretKeySpec secretKey = null;
        try {
            byte [] key = originalKey.getBytes(StandardCharsets.UTF_8);
            key =  MessageDigest.getInstance("SHA-1").digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return secretKey;
    }

    @Override
    public Optional<String> encrypt(String data, String key){
        SecretKeySpec secretKey = getKey(key);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
            return Optional.of(Base64.getEncoder()
                    .encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8))));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> decrypt(String data, String key){
        SecretKeySpec secretKey = getKey(key);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
            return Optional.of(new String(cipher.doFinal(Base64.getDecoder().decode(data))));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }







}
