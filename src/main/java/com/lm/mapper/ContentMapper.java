package com.lm.mapper;

import com.lm.domain.Content;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Louie on 2017-07-03.
 */
public interface ContentMapper {

    @Select("select * from content")
    List<Content> selectAll();

    @Insert("insert into content(title,createname,lasteditname,creattime,edittime,state,content,description,url,cid) values(#{title},#{createname},#{lasteditname},#{creattime},#{edittime},#{state},#{content},#{description},#{url},#{cid})")
    Integer add(
            @Param("title") String title,
            @Param("createname") String createname,
            @Param("lasteditname") String lasteditname,
            @Param("creattime") String creattime,
            @Param("edittime") String edittime,
            @Param("state") byte state,
            @Param("content") String content,
            @Param("description") String description,
            @Param("url") String url,
            @Param("cid") Integer cid);

    @Select("select * from content where id = #{id}")
    Content selectById(@Param("id") Integer id);

    @Select("select * from content where cid = #{cid}")
    Content selectByCid(@Param("cid") Integer cid);
}
