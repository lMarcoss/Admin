package com.system.executecommandlinux.controller;

import com.system.configuration.AdminProperties;
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
    private AdminProperties adminProperties;

    @Autowired
    private ExecuteBash executeBash;

    @GetMapping("/compileToMeltsan")
    public String compileToMeltsan() {
        try {
            final String nameShell = adminProperties.getNameShellMeltsan();
            executeBash.compileProjectSies(getPathShell(), nameShell);
            return "Compile and build project success: MELTSAN";
        } catch (Exception e) {
            return "ERROR COMPILE TO MELTSAN: " + e.getMessage();
        }
    }

    @GetMapping("/compileToLockton")
    public String compileToLocton() {
        try {
            final String nameShell = adminProperties.getNameShellLockton();
            executeBash.compileProjectSies(getPathShell(), nameShell);
            return "Compile and build project success: LOCKTON";
        } catch (Exception e) {
            return "ERROR COMPILE TO LOCKTON: " + e.getMessage();
        }
    }

    private String getPathShell() {
        return adminProperties.getPathShell();
    }
}
