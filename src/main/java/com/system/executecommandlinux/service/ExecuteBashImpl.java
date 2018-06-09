package com.system.executecommandlinux.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 07/06/2018
 * Time: 00:22
 */
@Service
public class ExecuteBashImpl implements ExecuteBash {
    @Override
    public void compileProjectSies() {
        //String command = "/opt/lockton/PROJECT/execute_script.sh";
        String command = "/Users/lMarcoss/workspace-meltsan/Lockton/execute_script.sh";
        String base = "/bin/sh ";
        try {
            Runtime.getRuntime().exec(base + command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
