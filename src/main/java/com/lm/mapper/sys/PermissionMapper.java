package com.lm.mapper.sys;

import com.lm.domain.shiro.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Louie on 2017-09-03.
 */
public interface PermissionMapper {

    @Select("select * from permission")
    List<Permission> selectAll();
}
