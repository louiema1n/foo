package com.lm.mapper;

import com.lm.domain.Tmp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Louie on 2017-07-25.
 */
public interface TmpMapper {

    @Insert("insert into tmp (content) values(#{content})")
    Integer insert(@Param("content") String content);

    @Select("select * from tmp")
    List<Tmp> selectAll();
}
