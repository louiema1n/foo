package com.lm.mapper;

import com.lm.domain.Store;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Louie on 2017-08-12.
 */
public interface StoreMapper {

    @Select("select * from store where name LIKE CONCAT('%',#{search},'%')")
    List<Store> selectAll(@Param("search") String search);

    @Select("select * from store where name LIKE CONCAT('%',#{search},'%') and state > 0")
    List<Store> selectAble(@Param("search") String search);

    @Select("select * from store where id = #{id}")
    Store selectById(@Param("id") long id);

    @Update("update store set name=#{name},size=#{size},sizeUnit=#{sizeUnit},total=#{total},totalUnit=#{totalUnit},safeCount=#{safeCount},state=#{state},afterAdd=#{afterAdd},used=#{used},afterAddTime=#{afterAddTime} where id=#{id}")
    void updateById(Store store);

    @Insert("insert into store (name,size,sizeUnit,total,totalUnit,safeCount,state,afterAdd,used,afterAddTime,createTime,creator) values(#{name},#{size},#{sizeUnit},#{total},#{totalUnit},#{safeCount},#{state},#{afterAdd},#{used},#{afterAddTime},#{createTime},#{creator})")
    void insert(Store store);

    @Delete("delete from store where id=#{id}")
    Integer delById(@Param("id") long id);

}
