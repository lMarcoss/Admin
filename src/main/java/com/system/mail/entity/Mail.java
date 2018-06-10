package com.system.mail.entity;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 09/06/2018
 * Time: 15:05
 */
public class Mail {
    private String mailTo;
    private String nameFile;

    public Mail() {
    }

    public Mail(String mailTo, String nameFile) {
        this.mailTo = mailTo;
        this.nameFile = nameFile;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "mailTo='" + mailTo + '\'' +
                ", nameFile='" + nameFile + '\'' +
                '}';
    }
}
