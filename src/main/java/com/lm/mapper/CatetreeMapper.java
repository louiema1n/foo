package com.lm.mapper;

import com.lm.domain.Catetree;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Louie on 2017-07-03.
 */
public interface CatetreeMapper {

    @Select("select * from catetree")
    List<Catetree> selectAll();

    @Insert("insert into catetree(pid,name,url,open,checked,path) values(#{pid},#{name},#{url},#{open},#{checked}, #{path})")
    Integer insert(
            @Param("pid") Integer pid,
            @Param("name") String name,
            @Param("url") String url,
            @Param("open") byte open,
            @Param("checked") byte checked,
            @Param("path") String path
    );
}
