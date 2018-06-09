package com.system.executecommandlinux.controller;

import com.system.executecommandlinux.service.ExecuteBash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 07/06/2018
 * Time: 00:21
 */
@RestController
@RequestMapping("/compileSies")
public class ExecuteCommanLinuxController {

    @Autowired
    private ExecuteBash executeBash;

    @GetMapping("/compile")
    public String compileSies() {
        try {
            executeBash.compileProjectSies();
            return "Compile success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
