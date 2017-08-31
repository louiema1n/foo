package com.lm.utils;

import com.lm.domain.shiro.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 根据username对密码进行加密
 * Created by Louie on 2017-08-31.
 */
public class MD5PwdUtil {
    public static User md52PasswordByUser(User user) {
        // 生成salt
        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        String salt = secureRandomNumberGenerator.nextBytes().toHex();
        // 设置盐
        user.setSalt(salt);

        // 根据username、password、salt对密码进行加密
        String password_cipherText = new Md5Hash(user.getPassword(), user.getUsername() + salt, 2).toHex();
        user.setPassword(password_cipherText);
        return user;
    }
}
