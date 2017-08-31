package com.lm.mapper;

import com.lm.domain.Catetree;
import org.apache.ibatis.annotations.*;

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

    @Select("SELECT LAST_INSERT_ID()")
    Integer returnId();

    @Delete("delete from catetree where id = #{id}")
    Integer delById(@Param("id") Integer id);

    @Update("update catetree set pid=#{pid},name=#{name},url=#{url},open=#{open},checked=#{checked},path=#{path} where id=#{id}")
    Integer upd(
            @Param("id") Integer id,
            @Param("pid") Integer pid,
            @Param("name") String name,
            @Param("url") String url,
            @Param("open") byte open,
            @Param("checked") byte checked,
            @Param("path") String path
    );
}
