package com.hcr.shiro.realm;

import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.UUID;

/**
 * @ClassName: ShiroMd5
 * @Description: shiro使用MD5明文加密输出密码
 * @Author: Arvin @hand-china.com
 * @Date: 2020/11/23
 * @Since version-1.0
 **/
public class ShiroMd5 {
    public static void main(String[] args) {
        int hashIterations = 1024;//加密的次数
        //Object salt = "arvin";//盐值
        Object credentials = "123456";//密码
        String hashAlgorithmName = "MD5";//加密方式
        String salt = UUID.randomUUID().toString();
        Object simpleHash = new SimpleHash(hashAlgorithmName, credentials,
                salt, hashIterations);
        System.out.println("加密后的值----->" + simpleHash);
        System.out.println("盐值：" + salt);
    }
}
