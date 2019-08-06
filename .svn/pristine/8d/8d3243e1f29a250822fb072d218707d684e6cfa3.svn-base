package com.capgemini.vuzixscanner.utils;

import android.util.Log;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by sphansek on 6/15/2017.
 */

public class SecurityUtils {
    private static final String LOG_TAG = SecurityUtils.class.getSimpleName();

    static char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private String initializeVector = "073e0e8fa5ff1e54";
    private IvParameterSpec ivspec;
    private SecretKeySpec keyspec;
    private Cipher cipher;

    private String SecretKey = "3798460f5c4a01e1";

    public SecurityUtils() {
        ivspec = new IvParameterSpec(initializeVector.getBytes());

        keyspec = new SecretKeySpec(SecretKey.getBytes(), "AES");

        try {
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        } catch (NoSuchPaddingException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }
    /**
     *
     * @param text
     * @return
     * @throws Exception
     */
    public byte[] encrypt(final String text) throws Exception {
        if (text == null)// || text.length() == 0
            throw new Exception("Empty string");

        byte[] encrypted = null;

        try {
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            encrypted = cipher.doFinal(padString(text).getBytes());
        } catch (Exception e) {
            throw new Exception("[encrypt] " + e.getMessage());
        }

        return encrypted;
    }
    /**
     *
     * @param code
     * @return
     * @throws Exception
     */
    public byte[] decrypt(final String code) throws Exception {
        if (code == null)// || code.length() == 0
            throw new Exception("Empty string");

        byte[] decrypted = null;

        try {
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            decrypted = cipher.doFinal(hexToBytes(code));
            // Remove trailing zeroes
            if (decrypted.length > 0) {
                int trim = 0;
                for (int i = decrypted.length - 1; i >= 0; i--)
                    if (decrypted[i] == 0)
                        trim++;

                if (trim > 0) {
                    final byte[] newArray = new byte[decrypted.length - trim];
                    System.arraycopy(decrypted, 0, newArray, 0,
                            decrypted.length - trim);
                    decrypted = newArray;
                }
            }
        } catch (Exception e) {
            throw new Exception("[decrypt] " + e.getMessage());
        }
        return decrypted;
    }
    /**
     *
     * @param buf
     * @return
     */
    public static String bytesToHex(final byte[] buf) {
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i) {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }
    /**
     *
     * @param str
     * @return
     */
    public static byte[] hexToBytes(final String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            final int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(
                        str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }
    }
    /**
     *
     * @param source
     * @return
     */
    private static String padString(String source) {
        final char paddingChar = 0;
        final int size = 16;
        final int x = source.length() % size;
        final int padLength = size - x;

        for (int i = 0; i < padLength; i++) {
            source += paddingChar;
        }
        return source;
    }
}
