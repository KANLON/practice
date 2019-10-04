package com.kanlon;

import com.kanlon.util.AesUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * aes加密，解密测试
 *
 * @author zhangcanlong
 * @since 2019/10/4 20:58
 **/
public class AesUtilTest {

    @Test
    public void testAesEncodeAndDecode() {
        final String key = "123456";
        final String content = "test";
        final String content2 = "Test2asdfljkjsdlfjlsfdsffffffdddddddddddddd@@@.";
        String decrypt = "";
        String encrypt = "";
        try {
            encrypt = AesUtil.aesPKCS5PaddingEncrypt(content2, key);
            decrypt = AesUtil.aesPKCS5PaddingDecrypt(encrypt, key);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(decrypt, content2);
        System.out.println("加密后的内容：" + encrypt + ",长度为：" + encrypt.length());
        System.out.println("解密后的内容：" + decrypt + ",长度为：" + decrypt.length());
    }
}
