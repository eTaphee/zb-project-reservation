package com.zeroboase.reservation.domain.common.encrypt;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptComponent {

    @Value("${options.secretKey}")
    private String secretKey;

    private final Base64.Encoder encoder = Base64.getEncoder();
    private final Base64.Decoder decoder = Base64.getDecoder();

    public String encryptString(String encryptString) throws Exception {
        Cipher cipher = cipherPkcs5(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedString = cipher.doFinal(encryptString.getBytes(UTF_8));
        return new String(encoder.encode(encryptedString));
    }

    public String decryptString(String decryptString) throws Exception {
        byte[] byteString = decoder.decode(decryptString.getBytes(UTF_8));
        Cipher cipher = cipherPkcs5(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(byteString));
    }

    private static Cipher cipherPkcs5(int opMode, String secretKey) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec sk = new SecretKeySpec(secretKey.getBytes(UTF_8), "AES");
        IvParameterSpec iv = new IvParameterSpec(secretKey.substring(0, 16).getBytes(UTF_8));
        c.init(opMode, sk, iv);
        return c;
    }
}
