package com.lm.service.sys;

import com.lm.domain.shiro.Permission;
import com.lm.mapper.sys.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Louie on 2017-09-03.
 */
@Service
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    public List<Permission> getAll() {
        return this.permissionMapper.selectAll();
    }
}
