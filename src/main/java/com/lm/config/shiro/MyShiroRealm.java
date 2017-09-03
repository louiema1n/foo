package com.lm.config.shiro;

import com.lm.domain.shiro.User;
import com.lm.service.sys.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 身份验证核心类
 * Created by Louie on 2017-08-29.
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 权限控制
     * 次方法在调用hasRealm,hasPermission的时候才会回调
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 获取当前用户
        User user = (User) principalCollection.getPrimaryPrincipal();
        // 遍历当前用户权限并验证
//        for (Role role : user.getRoleList()) {
//            authorizationInfo.addRole(role.getRole());
//            for (Permission permission : role.getPermissionList()) {
//                authorizationInfo.addStringPermission(permission.getPermission());
//            }
//        }
        return authorizationInfo;
    }

    /**
     * 身份验证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取用户输入的用户名
        String username = (String) authenticationToken.getPrincipal();
        // 根据该username从数据库中进行查询
        User user = this.userService.findByUsername(username);
        if (user == null) {
            // 用户名不存在
            throw new UnknownAccountException();
        }
        if (user.getState() == 2) {
            // 已锁定
            throw new LockedAccountException();
        }
        if (user.getState() == 3) {
            // 已禁用
            throw new DisabledAccountException();
        }
        if (user.getState() == 0) {
            // 未激活
            throw new ExpiredCredentialsException();
        }

        // 交给AuthenticatingRealm的CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,               // 用户
                user.getPassword(), // 密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),   // 加密盐
                getName());
        return authenticationInfo;
    }
}
