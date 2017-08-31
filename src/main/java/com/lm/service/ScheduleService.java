package com.lm.service;

import com.lm.domain.Schedule;
import com.lm.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Louie on 2017-08-21.
 */
@Service
public class ScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;

    public List<Schedule> queryAll(Date beginDay, Date endDay) {
        return this.scheduleMapper.selectAll(beginDay, endDay);
    }

    public Integer batchAdd(List<Schedule> schedules) {
        return this.scheduleMapper.batchInsertSch(schedules);
    }
}
