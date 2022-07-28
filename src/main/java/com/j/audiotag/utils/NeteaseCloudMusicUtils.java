package com.j.audiotag.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import lombok.experimental.UtilityClass;

import java.math.BigInteger;
import java.util.Map;

/**
 * 网易云音乐接口加密工具
 * @author Jason
 * @since 2022-07-06 09:05:53
 */
@UtilityClass
public class NeteaseCloudMusicUtils {

    private String AES_KEY = "0CoJUm6Qyw8W8jud";

    private String IV = "0102030405060708";

    private String RSA_PUB_KEY = "010001";

    private String RSA_MODULUS = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";

    /**
     * 参数加密
     * @param source
     * @return
     */
    public Map<String, String> encrypt(String source) {
        Map<String, String> map = MapUtil.newHashMap();
        String nonce = RandomUtil.randomString(16);
        map.put("params", aesEncrypt(aesEncrypt(source, AES_KEY), nonce));
        map.put("encSecKey", rsaEncrypt(nonce));
        return map;
    }

    /**
     * AES 加密
     * @param source
     * @param key
     * @return
     */
    private String aesEncrypt(String source, String key) {
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, StrUtil.bytes(key), StrUtil.bytes(IV));
        return aes.encryptBase64(source);
    }

    /**
     * RSA 加密
     * @param source
     * @return
     */
    private String rsaEncrypt(String source) {
        // 需要先将加密的消息翻转，再进行加密
        source = StrUtil.reverse(source);
        // 转十六进制字符串
        String secKeyHex = HexUtil.encodeHexStr(source);
        // 指定基数的数值字符串转换为BigInteger表示形式
        BigInteger biText = new BigInteger(secKeyHex, 16);
        BigInteger biEx = new BigInteger(RSA_PUB_KEY, 16);
        BigInteger biMod = new BigInteger(RSA_MODULUS, 16);
        // 次方并求余（biText^biEx mod biMod is ?）
        BigInteger bigInteger = biText.modPow(biEx, biMod);
        return zFill(bigInteger.toString(16), 256);
    }

    /**
     * 补0
     * @param str
     * @param size
     * @return
     */
    private String zFill(String str, int size) {
        StringBuilder builder = new StringBuilder(str);
        while (builder.length() < size) {
            builder.insert(0, "0");
        }
        return builder.toString();
    }
}
