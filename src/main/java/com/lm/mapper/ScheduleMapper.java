package com.lm.mapper;

import com.lm.domain.Schedule;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Louie on 2017-08-21.
 */
public interface ScheduleMapper {

//    @Select("select * from schedule")
//    List<Schedule> selectAll();

    @Select("select * from schedule where schtime >= #{beginDay} and schtime <= #{endDay} order by schtime asc")
    @Results({
            @Result(property = "cid", column = "cid"),
            @Result(property = "uid", column = "uid"),
            @Result(property = "aClass", column = "cid", one = @One(select = "com.lm.mapper.ClassMapper.selectById")),
            @Result(property = "user", column = "uid", one = @One(select = "com.lm.mapper.sys.UserMapper.selectById"))
    })
    List<Schedule> selectAll(@Param("beginDay") Date beginDay, @Param("endDay") Date endDay);

    @InsertProvider(type = ScheduleDaoProvider.class, method = "batchInsert")
    Integer batchInsertSch(List<Schedule> schedules);

    // 动态sql拼接
    class ScheduleDaoProvider {
        public String batchInsert(Map map) {
            String sql = "insert into schedule(schtime, cid, uid) values";
            List<Schedule> schedules = (List<Schedule>) map.get("list");
            for (Schedule schedule : schedules) {
                sql += "('" + schedule.getSchtime() + "', " + schedule.getCid() + ", " + schedule.getUid() + "),";
            }
            // 剪掉最后一个,
            sql = sql.substring(0, sql.lastIndexOf(","));
            return sql;
        }
    }
}
