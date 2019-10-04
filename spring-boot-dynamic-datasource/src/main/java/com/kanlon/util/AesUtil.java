package com.kanlon.util;


import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES加密算法util
 * Created by steadyjack on 2018/4/21.
 * 参考：https://www.jianshu.com/p/ae7178f28e36
 *
 * @author zhangcanlong
 * @since 2019-10-04
 */
public class AesUtil {

    private static final String ENCRYPT_ALG = "AES";

    private static final String CIPHER_MODE = "AES/ECB/PKCS5Padding";

    private static final String ENCODE = "UTF-8";

    private static final int SECRET_KEY_SIZE = 32;

    private static final String KEY_ENCODE = "UTF-8";

    /**
     * AES/ECB/PKCS5Padding 加密
     *
     * @param content 明文
     * @param key     密钥
     * @return aes加密后 转base64 的密文
     */
    public static String aesPKCS5PaddingEncrypt(final String content, final String key) {
        final String result;
        try {
            final Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            final byte[] realKey = getSecretKey(key);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(realKey, ENCRYPT_ALG));
            final byte[] data = cipher.doFinal(content.getBytes(ENCODE));
            result = new Base64().encodeToString(data);
        } catch (final UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new RuntimeException("加密失败！", e);
        }
        return result;
    }

    /**
     * AES/ECB/PKCS5Padding 解密
     *
     * @param content aes加密过的密文
     * @param key     密钥
     * @return 先转base64 再解密,的明文
     */
    public static String aesPKCS5PaddingDecrypt(final String content, final String key) {
        final byte[] decodeBytes = Base64.decodeBase64(content);
        final byte[] realKey;
        final byte[] realBytes;
        final String returnStr;
        try {
            final Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            realKey = getSecretKey(key);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(realKey, ENCRYPT_ALG));
            realBytes = cipher.doFinal(decodeBytes);
            returnStr = new String(realBytes, ENCODE);
        } catch (final UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new RuntimeException("解密失败", e);
        }
        return returnStr;

    }

    /**
     * 对密钥key进行处理：如密钥长度不够位数的则 以指定paddingChar 进行填充；
     * 此处用空格字符填充
     *
     * @param key 需要填充的密钥
     * @return 返回填从后的密钥
     */
    private static byte[] getSecretKey(final String key) throws UnsupportedEncodingException {
        final byte paddingChar = ' ';
        final byte[] realKey = new byte[SECRET_KEY_SIZE];
        final byte[] byteKey = key.getBytes(KEY_ENCODE);
        for (int i = 0; i < realKey.length; i++) {
            if (i < byteKey.length) {
                realKey[i] = byteKey[i];
            } else {
                realKey[i] = paddingChar;
            }
        }
        return realKey;
    }
}
