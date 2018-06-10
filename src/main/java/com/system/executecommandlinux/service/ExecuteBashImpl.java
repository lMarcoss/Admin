package com.system.executecommandlinux.service;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 07/06/2018
 * Time: 00:22
 */
@Service
public class ExecuteBashImpl implements ExecuteBash {
    private static Logger LOG = Logger.getLogger(ExecuteBashImpl.class);

    @Override
    public void compileProjectSies(String path, String nameShell) {
        final File executorDirectory = new File(path);

        ProcessBuilder processBuilder = new ProcessBuilder(nameShell);
        processBuilder.directory(executorDirectory);
        Process process;
        try {
            int shellExitStatus;
            process = processBuilder.start();
            shellExitStatus = process.waitFor();
            LOG.info(shellExitStatus);
        } catch (IOException | InterruptedException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        }
    }
}
