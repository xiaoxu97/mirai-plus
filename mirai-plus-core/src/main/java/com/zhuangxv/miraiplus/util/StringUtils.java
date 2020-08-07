package com.zhuangxv.miraiplus.util;

import com.zhuangxv.miraiplus.exception.MiraiPlusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author xiaoxu
 */
public class StringUtils {

    public static boolean isBlank(String string) {
        if (string == null) {
            return true;
        }
        return "".equals(string.trim());
    }

    public static boolean isNotBlank(String string) {
        if (string == null) {
            return false;
        }
        return !"".equals(string.trim());
    }

    public static String createRandomNumber(Integer count) {
        String sources = "0123456789";
        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for (int j = 0; j < count; j++) {
            randomNumber.append(sources.charAt(random.nextInt(9)));
        }
        return randomNumber.toString();
    }

    public static String getSha256(String string) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(string.getBytes(StandardCharsets.UTF_8));
            return byte2Hex(messageDigest.digest());
        } catch (Exception e) {
            throw new MiraiPlusException(99999, "未知错误", "getSHA256异常");
        }
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }

    public static String toLowerCaseFirstOne(String string) {
        if (StringUtils.isBlank(string) && string.length() < 2) {
            return null;
        }
        if (Character.isLowerCase(string.charAt(0))) {
            return string;
        } else {
            return Character.toUpperCase(string.charAt(0)) + string.substring(1);
        }
    }

    public static String getSimpleClassName(Class<?> cClass) {
        return StringUtils.toLowerCaseFirstOne(cClass.getSimpleName());
    }

    public static String getMd5(String str) {
        return getMd5(str.getBytes());
    }

    public static String getMd5(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte[] bytes1 = md.digest();
            int i;
            StringBuilder stringBuilder = new StringBuilder();
            for (byte value : bytes1) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(Integer.toHexString(i));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new MiraiPlusException(0, e.getMessage(), "{}", e);
        }
    }

}
