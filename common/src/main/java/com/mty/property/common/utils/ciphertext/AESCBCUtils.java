package com.mty.property.common.utils.ciphertext;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author mty
 * @since 2022/09/15 17:36
 **/
public class AESCBCUtils {
    private static String cipherType = "AES/CBC/PKCS5Padding";
    private static String charsetName = "UTF-8";

    public static String encrypt(String sSrc, String encodingFormat, String sKey,
                                 String ivParameter) throws Exception {
        Cipher cipher = Cipher.getInstance(cipherType);
        byte[] raw = sKey.getBytes(charsetName);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));
        return new BASE64Encoder().encode(encrypted).replaceAll(System.lineSeparator(), "");
    }

    public static String decrypt(String sSrc, String encodingFormat, String sKey, String ivParameter) throws Exception {
        byte[] raw = sKey.getBytes(charsetName);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(cipherType);
        IvParameterSpec IV = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, IV);
        byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original, encodingFormat);

    }
}
