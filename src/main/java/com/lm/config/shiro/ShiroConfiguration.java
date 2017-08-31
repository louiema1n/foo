package com.lm.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 * 通过filter实现
 * Created by Louie on 2017-08-29.
 */
@Configuration
public class ShiroConfiguration {

    /**
     * shiro拦截器配置
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 配置拦截器-->从上至下顺序执行（/**放最下边）
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置退出过滤器，shiro已经为我们具体实现
        filterChainDefinitionMap.put("/logout", "logout");
        // 设置静态资源访问路径
        filterChainDefinitionMap.put("/adminlte/**", "anon");
        filterChainDefinitionMap.put("/bootstrap-table/**", "anon");
        filterChainDefinitionMap.put("/ckeditor/**", "anon");
        filterChainDefinitionMap.put("/fileinput/**", "anon");
        filterChainDefinitionMap.put("/font-awesome/**", "anon");
        filterChainDefinitionMap.put("/jqprint/**", "anon");
        filterChainDefinitionMap.put("/myCustom/**", "anon");
        filterChainDefinitionMap.put("/zTree/**", "anon");

        // 解决login成功下载favicon.ico的问题
//        filterChainDefinitionMap.put("/static/**", "anon");
        // 或者
        filterChainDefinitionMap.put("/favicon.ico", "anon");

        // 配置rememberMe或者认证通过可以访问的url
        filterChainDefinitionMap.put("/index", "user");
        filterChainDefinitionMap.put("/", "user");

        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        filterChainDefinitionMap.put("/**", "authc");

        // 设置登录html
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 设置登陆成功后跳转的html
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 设置未授权html
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * shiro安全管理器
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 注入自定义realm
        securityManager.setRealm(myShiroRealm());
        // 注入Ehcache缓存
//        securityManager.setCacheManager(ehCacheManager());
        // 注入rememberMe管理器对象
        securityManager.setRememberMeManager(cookieRememberMeManager());
        return securityManager;
    }

    /**
     * 身份验证Realm，需要自己实现
     * @return
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        // 注入凭证匹配器
        myShiroRealm.setCredentialsMatcher(credentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 凭证匹配器
     * @return
     */
    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");     // 散列算法-->md5
        credentialsMatcher.setHashIterations(2);            // 散列2次-->md5(md5(""))
        return credentialsMatcher;
    }

    /**
     * 开启shiro AOP注解支持
     * 使用代理方式必须开启
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * ehcache缓存管理器
     * @return
     */
//    @Bean
//    public EhCacheManager ehCacheManager() {
//        EhCacheManager ehCacheManager = new EhCacheManager();
//        ehCacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
//        return ehCacheManager;
//    }

    /**
     * 简单cookie对象
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        // arg对应前端的CheckBox的name值
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // cookie生效实践，单位：s
        simpleCookie.setMaxAge(30);
        return simpleCookie;
    }

    /**
     * rememberMe cookie管理对象
     * @return
     */
    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    /**
     * 使thymeleaf支持shiro标签
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
