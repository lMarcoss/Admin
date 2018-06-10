package com.system.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 09/06/2018
 * Time: 00:00
 */
@Component
@PropertySource("classpath:admin-${spring.profiles.active}.properties")
@ConfigurationProperties
public class AdminProperties {
    private String mailFrom;
    private String directorySiesLog;
    private String patternFileSiesLog;
    private String fileLogCompileSies;
    private String fileWarSies;
    private String pathShell;
    private String nameShellMeltsan;
    private String nameShellLockton;

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getDirectorySiesLog() {
        return validatePath(directorySiesLog);
    }

    public void setDirectorySiesLog(String directorySiesLog) {
        this.directorySiesLog = directorySiesLog;
    }

    public String getPatternFileSiesLog() {
        return patternFileSiesLog;
    }

    public void setPatternFileSiesLog(String patternFileSiesLog) {
        this.patternFileSiesLog = patternFileSiesLog;
    }

    public String getFileLogCompileSies() {
        return fileLogCompileSies;
    }

    public void setFileLogCompileSies(String fileLogCompileSies) {
        this.fileLogCompileSies = fileLogCompileSies;
    }

    public String getFileWarSies() {
        return fileWarSies;
    }

    public void setFileWarSies(String fileWarSies) {
        this.fileWarSies = fileWarSies;
    }

    public String getPathShell() {
        return validatePath(pathShell);
    }

    public void setPathShell(String pathShell) {
        this.pathShell = pathShell;
    }

    public String getNameShellMeltsan() {
        return nameShellMeltsan;
    }

    public void setNameShellMeltsan(String nameShellMeltsan) {
        this.nameShellMeltsan = nameShellMeltsan;
    }

    public String getNameShellLockton() {
        return nameShellLockton;
    }

    public void setNameShellLockton(String nameShellLockton) {
        this.nameShellLockton = nameShellLockton;
    }

    private String validatePath(String path) {
        if (path.trim().substring(path.length() - 1).equals("/")) {
            return path.trim();
        } else {
            return path.trim() + "/";
        }
    }
}
