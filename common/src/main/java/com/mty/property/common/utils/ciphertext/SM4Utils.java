package com.mty.property.common.utils.ciphertext;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.lang.copier.SrcToDestCopier;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

/**
 * @author mty
 * @date 2022/09/16 10:22
 **/
@Slf4j
public class SM4Utils {
    // 编码
    private static final String ENCODING = "UTF-8";
    // 机密名称
    public static final String ALGORITHM_NAME = "SM4";
    // 加密分组方式
    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS7Padding";
    // KEY长度
    public static final int DEFAULT_KEY_SIZE = 128;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    // 生成指定长度密钥
    public static byte[] generateKey() throws Exception {
        return generateKey(DEFAULT_KEY_SIZE);
    }

    public static byte[] generateKey(int keySize) throws Exception {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
            kg.init(keySize, new SecureRandom());
            return kg.generateKey().getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm -->[" + ALGORITHM_NAME + "]");
        }
    }

    // 生成ecg暗号
    private static Cipher generateEcbCipher(String algorithmName, int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, algorithmName);
        cipher.init(mode, sm4Key);
        return cipher;
    }

    /**
     * 加密后返回16进制数据
     *
     * @param hexKey
     * @param paramStr
     * @param charset
     * @return
     * @throws Exception
     */
    public static String sm4Encrypt(String hexKey, String paramStr, String charset) throws Exception {
        String cipherText = "";
        try {
            if (null != paramStr && !"".equals(paramStr)) {
                byte[] keyData = ByteUtils.fromHexString(hexKey);
                charset = charset.trim();
                if (charset.length() <= 0) {
                    charset = ENCODING;
                }
                byte[] srcData = paramStr.getBytes(charset);
                byte[] cipherArray = encryptEcbPadding(keyData, srcData);
                cipherText = ByteUtils.toHexString(cipherArray);
            }
        } catch (Exception e) {
            log.error("sm4Encrypt error, Exception: {}", e.getMessage());
        }
        return cipherText;
    }

    /**
     * 加密后返回Base64数据
     *
     * @param key
     * @param paramStr
     * @param charset
     * @return
     * @throws Exception
     */
    public static String sm4EncryptByBase64(String key, String paramStr, String charset) throws Exception {
        String cipherText = "";
        try {
            if (null != paramStr && !"".equals(paramStr)) {
                byte[] keyData = new BASE64Decoder().decodeBuffer(key);
                charset = charset.trim();
                if (charset.length() <= 0) {
                    charset = ENCODING;
                }
                byte[] srcData = paramStr.getBytes(charset);
                byte[] cipherArray = encryptEcbPadding(keyData, srcData);
                cipherText = new BASE64Encoder().encode(cipherArray).replaceAll(System.lineSeparator(), "");
            }
        } catch (Exception e) {
            log.error("[base64] sm4encrypt error, exception: {}", e.getMessage());
        }
        return cipherText;
    }

    /**
     * sm4解密Base64数据
     *
     * @param key
     * @param cipherText
     * @param charset
     * @return
     * @throws Exception
     */
    public static String sm4DecryptbyBase64(String key, String cipherText, String charset) throws Exception {
        String decryptStr = "";
        try {
            byte[] keyData = new BASE64Decoder().decodeBuffer(key);
            byte[] cipherData = new BASE64Decoder().decodeBuffer(cipherText);
            byte[] srcData = decryptEcbPadding(keyData, cipherData);
            charset = charset.trim();
            if (srcData.length <= 0) {
                charset = ENCODING;
            }
            decryptStr = new String(srcData, charset);
        } catch (Exception e) {
            log.error("[base64] sm4Decrypt error, Exception: {}" + e.getMessage());
        }
        return decryptStr;
    }

    /**
     * sm4解密hex密文
     *
     * @param hexKey
     * @param cipherText
     * @param charset
     * @return
     * @throws Exception
     */
    public static String sm4Decrypt(String hexKey, String cipherText, String charset) throws Exception {
        String decryptStr = "";
        try {
            byte[] keyData = ByteUtils.fromHexString(hexKey);
            byte[] cipherData = ByteUtils.fromHexString(cipherText);
            byte[] stcData = decryptEcbPadding(keyData, cipherData);
            charset = charset.trim();
            if (charset.length() <= 0) {
                charset = ENCODING;
            }
            decryptStr = new String(stcData, charset);
        } catch (Exception e) {
            log.error("sm4Decrypt error, Exception: {}", e.getMessage());
        }
        return decryptStr;

    }

    // ECB加密模式
    public static byte[] encryptEcbPadding(byte[] key, byte[] data) throws Exception {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }


    // 解密
    public static byte[] decryptEcbPadding(byte[] key, byte[] cipherText) throws Exception {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    // 解密校验
    public static boolean verifyCipher(String hexKey, String cipherText, String paramStr) throws Exception {
        boolean flag = false;
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        byte[] dipherData = ByteUtils.fromHexString(cipherText);
        byte[] decryptData = decryptEcbPadding(keyData, dipherData);
        byte[] srcData = paramStr.getBytes(ENCODING);
        flag = Arrays.equals(decryptData, srcData);
        return flag;

    }
}
