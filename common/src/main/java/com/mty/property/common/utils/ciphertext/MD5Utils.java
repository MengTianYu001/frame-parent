package com.mty.property.common.utils.ciphertext;

import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.security.MessageDigest;

/**
 * @author mty
 * @date 2022/09/15 18:40
 **/
public class MD5Utils {
    public static String getMD5(String str) throws Exception {
        MessageDigest md5digest = MessageDigest.getInstance("MD5");
        byte[] digByteResult = md5digest.digest(str.getBytes("utf-8"));
        return new String((Hex.encode(digByteResult)));

    }
}
