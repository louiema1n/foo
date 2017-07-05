package com.lm.mapper;

import com.lm.domain.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Louie on 2017-06-26.
 */
public interface FileMapper {

    @Select("SELECT * FROM file")
    List<File> selectAll();

    @Insert("INSERT INTO file(oldname,newname,path,uploadtime,type,description,suffix,size) VALUES(#{oldname},#{newname},#{path},#{uploadtime},#{type},#{description},#{suffix},#{size})")
    Integer insert(@Param("oldname") String oldname,
                   @Param("newname") String newname,
                   @Param("path") String path,
                   @Param("uploadtime") String uploadtime,
                   @Param("type") String type,
                   @Param("description") String description,
                   @Param("suffix") String suffix,
                   @Param("size") Integer size);

    @Select("select count(*) from file")
    Integer count();

    @Select("select * from file where id = #{id}")
    File selectFileById(@Param("id") Integer id);

    @Delete("delete from file where id = #{id}")
    Integer delFileById(@Param("id") Integer id);
}
