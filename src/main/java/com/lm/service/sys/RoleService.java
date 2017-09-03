package com.lm.service.sys;

import com.lm.domain.shiro.Role;
import com.lm.mapper.sys.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Louie on 2017-09-03.
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> getAll() {
        return this.roleMapper.selectAll();
    }
}
