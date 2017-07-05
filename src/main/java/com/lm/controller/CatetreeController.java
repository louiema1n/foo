package com.lm.controller;

import com.lm.domain.Catetree;
import com.lm.service.CatetreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Louie on 2017-07-03.
 */
@RestController
@RequestMapping("catetree")
public class CatetreeController {

    @Autowired
    private CatetreeService catetreeService;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<Catetree> all() {
        return this.catetreeService.getAll();
    }
}
