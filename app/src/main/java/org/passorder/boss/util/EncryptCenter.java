package org.passorder.boss.util;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptCenter {

    private static final String AES_ENCRYPTION_KEY = "VKDOWHGMDKZOCLDJEKRTQOSNCMVZXXDL";
    private static final String INIT_VECTOR = "ZZXOCMDKFFOEQWSA";

    public static String encrypt(String raw) {

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(sha256Encrypt(AES_ENCRYPTION_KEY), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(Arrays.copyOfRange(sha256Encrypt(INIT_VECTOR), 0, 16));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            String result = makePlain(raw);

            byte[] results = cipher.doFinal(result.getBytes(StandardCharsets.UTF_8));

            return Base64.encodeToString(results, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] sha256Encrypt(String sha) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(sha.getBytes(StandardCharsets.UTF_8));

            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return new byte[32];
    }

    private static String makePlain(String text) {
        int times = (int) Math.floor(text.length() / 16) + 1;
        int zeroCount = 16 - (text.length() % 16);

        StringBuilder builder = new StringBuilder();

        // Put Zero String
        builder.append(zeroCount);

        // Put #s
        while (builder.length() != 16)
            builder.append("#");

        // Put raw text
        builder.append(text);

        // Put 0s
        while (builder.length() != 16 * (times + 1)) {
            builder.append("0");
        }

        // Example 7###############passorder0000000

        return builder.toString();
    }
}