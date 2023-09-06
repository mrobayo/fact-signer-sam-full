package com.marvic.factsigner.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesCipher {

    private static final Logger log = LoggerFactory.getLogger(AesCipher.class);

    private SecretKeySpec secretKey;
    private static byte[] key;
    private static final String ALGORITHM = "AES";

    public AesCipher(String secretKey) {
        prepareSecreteKey(secretKey);
    }

    public void prepareSecreteKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }
    }

    public String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            log.error("Encrypt fails - " + e.getMessage());
        }
        return null;
    }

    public String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            log.error("Decrypt fails - " + e.getMessage());
        }
        return null;
    }

//    public static void main(String[] args) {
//        final String secretKey = "secrete";
//
//        String originalString = "javaguides";
//
//        AesCipher aesEncryptionDecryption = new AesCipher(secretKey);
//        String encryptedString = aesEncryptionDecryption.encrypt(originalString);
//        String decryptedString = aesEncryptionDecryption.decrypt(encryptedString);
//
//        System.out.println(originalString);
//        System.out.println(encryptedString);
//        System.out.println(decryptedString);
//    }

}
