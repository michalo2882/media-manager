package meme.demo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Utils {

    public static String createRandomUuidString() {
        MessageDigest salt = null;
        try {
            salt = MessageDigest.getInstance("SHA-256");
            salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return bytesToHex(salt.digest());
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b: bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
