package com.lm.mapper;

import com.lm.domain.Class;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Louie on 2017-08-21.
 */
public interface ClassMapper {

    @Select("select * from class")
    List<Class> selectAll();

    @Select("select * from class where cid = #{cid}")
    Class selectById(long cid);
}
