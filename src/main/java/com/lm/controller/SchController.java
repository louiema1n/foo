package com.lm.controller;

import com.lm.domain.Schedule;
import com.lm.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Louie on 2017-08-21.
 */
@RestController
@RequestMapping("/sch")
public class SchController {
    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Schedule> getAll(@RequestParam("beginDay") String beginDay, @RequestParam("endDay") String endDay) throws ParseException {
        return this.scheduleService.queryAll(new SimpleDateFormat("yyyy-MM-dd").parse(beginDay), (new SimpleDateFormat("yyyy-MM-dd").parse(endDay)));
    }
}
