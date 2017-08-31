package com.lm.service;

import com.lm.domain.shiro.User;
import com.lm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Louie on 2017-08-21.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> queryAllSch() {
        return this.userMapper.selectAllSch();
    }

    public List<User> queryAll() {
        return this.userMapper.selectAll();
    }

    public User findByUsername(String username) {
        return this.userMapper.findByUsername(username);
    }

    /**
     * 锁定用户
     * @param user
     * @return
     */
    public Integer updAccountByUser(User user) {
        return this.userMapper.updAccountByUser(user);
    }
}
