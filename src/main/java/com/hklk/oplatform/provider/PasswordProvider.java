package com.hklk.oplatform.provider;

import com.hklk.oplatform.exception.ServiceException;
import com.hklk.oplatform.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码加密实现提供者
 */
public class PasswordProvider {

    private static final String SUFFIX = "`1qazx";

    /**
     * 加密
     *
     * @param password Md5密码
     * @return
     */
    public static String encrypt(String password) {
        if (StringUtils.isBlank(password)) {
            throw new ServiceException("密码不能为空");
        }
        try {
            return md5(new StringBuilder(password).append(SUFFIX).toString());
        } catch (Exception e) {
            throw new ServiceException("密码加密错误");
        }
    }

    public static String md5(String str) {
        String password = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            password = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }


    public static String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }




    public static void main(String[] args) throws FileNotFoundException {
        System.err.println("加密        后:" + encrypt("12345678"));
        File file = new File("D:\\zookeeper-3.4.12.tar.gz");
        System.out.println(getMd5ByFile(file));
    }
}
