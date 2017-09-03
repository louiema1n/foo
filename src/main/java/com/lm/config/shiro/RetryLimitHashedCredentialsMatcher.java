package com.lm.config.shiro;

import com.lm.domain.shiro.User;
import com.lm.service.sys.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 密码重试次数限制
 * Created by Louie on 2017-08-31.
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    private UserService userService;

    // 创建线程安全的缓存对象;arg0-->缓存名;arg1-->该缓存的重试次数
    private Cache<String, AtomicInteger> retryLimitCache;

    // 通过构造函数获得缓存对象
    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        this.retryLimitCache = cacheManager.getCache("retryLimitCache");
    }

    /**
     * 重写父类的doCredentialsMatch方法
     * 实现连续5次输入密码错误，限制登录1分钟
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // 获取当前登录用户名
        String username = (String) token.getPrincipal();
        // 根据username获取Ehcache中缓存的重试次数
        AtomicInteger retryCount = this.retryLimitCache.get(username);
        if (retryCount == null) {
            // 为空，则当前缓存中没有该username的缓存
            retryCount = new AtomicInteger(0);
            // 放入缓存
            this.retryLimitCache.put(username, retryCount);
        }
        // 调用父类的身份验证方法
        boolean match = super.doCredentialsMatch(token, info);
        if (match) {
            // 验证通过
            if (retryCount.get() < 5) {
                // 且重试次数小于5，则清除该缓存
                this.retryLimitCache.remove(username);
            } else if (retryCount.get() == 5) {
                // 重试次数为5，提醒用户被锁定1分钟，不能进行重试
                throw new ExcessiveAttemptsException();
            }
        } else {
            // 验证失败，重试次数自增
            retryCount.incrementAndGet();
            if (retryCount.get() == 5) {
                // 重试次数为5，提醒用户被锁定1分钟，不能进行重试
                throw new ExcessiveAttemptsException();
            }
            if (retryCount.get() > 5) {
                // 永久锁定该用户
                User user = new User();
                user.setUsername(username);
                user.setState(2);
                Integer i = this.userService.updAccountByUser(user);
                if (i > 0) {
                    throw new LockedAccountException();
                }
            }
        }
        return match;
    }
}
