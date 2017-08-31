package com.lm.controller;

import com.lm.domain.Schedule;
import com.lm.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Louie on 2017-08-21.
 */
@RestController
@RequestMapping("/sch")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

}
