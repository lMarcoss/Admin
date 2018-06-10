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
@PropertySource("classpath:admin.properties")
@ConfigurationProperties
public class AdminProperties {
    private String mailFrom;
    private String directorySiesLog;

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getDirectorySiesLog() {
        return directorySiesLog;
    }

    public void setDirectorySiesLog(String directorySiesLog) {
        this.directorySiesLog = directorySiesLog;
    }
}
