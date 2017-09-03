package com.lm.mapper.sys;

import com.lm.domain.shiro.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Louie on 2017-09-03.
 */
public interface RoleMapper {

    @Select("select * from role")
    List<Role> selectAll();
}
