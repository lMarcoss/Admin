package com.system.mail.service;

import com.system.mail.entity.Mail;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 08/06/2018
 * Time: 23:40
 */
public interface MailService {
    void sendMailSimple(String email) throws Exception;

    void sendMailWithFile(Mail mail) throws Exception;
}
