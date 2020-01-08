package com.company.Service;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptionService {


    public static String encrypt(String key, String data){
        String[] keys = prepareKeys(key);
        String s = "";
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            IvParameterSpec ps = new IvParameterSpec(keys[0].getBytes(StandardCharsets.UTF_8));
            SecretKeySpec ss = new SecretKeySpec(keys[1].getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, ss, ps);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            s = Base64.encodeBase64String(encrypted);
        }catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException |
                BadPaddingException e){
            e.printStackTrace();
        }
        return s;
    }

    public static String decrypt(String key, String data){
        String[] keys = prepareKeys(key);
        String s = "";
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            IvParameterSpec ps = new IvParameterSpec(keys[0].getBytes(StandardCharsets.UTF_8));
            SecretKeySpec ss = new SecretKeySpec(keys[1].getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.DECRYPT_MODE, ss, ps);
            byte[] decrypted = cipher.doFinal(Base64.decodeBase64(data));
            s = new String(decrypted);
        }catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException |
                BadPaddingException e){
            e.printStackTrace();
        }
        return s;
    }
    public static String[] prepareKeys(String s){
        String[] result = new String[2];
        while (s.length()<16){
            s +=s;
        }
        result[0] = s.substring(0,16);
        result[1] = s;
        return result;
    }
}
