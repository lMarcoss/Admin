package com.system.mail.controller;

import com.system.mail.entity.Mail;
import com.system.mail.service.MailService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 09/06/2018
 * Time: 00:19
 */
@RestController
@RequestMapping("/mail")
public class MailController {
    private static Logger LOG = Logger.getLogger(MailController.class);
    private static String MESSAGE_SENT = "Message sent";

    @Autowired
    private MailService mailService;

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody Mail mail) {
        try {
            LOG.info(mail.toString());
            mailService.sendMailSimple(mail.getMailTo());
            return MESSAGE_SENT;
        } catch (Exception e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
            return e.getMessage();
        }
    }

    @PostMapping("/sendMailWithFile")
    public String sendMailWithFile(@RequestBody Mail mail) {
        try {
            LOG.info(mail.toString());
            mailService.sendMailWithFile(mail);
            return MESSAGE_SENT;
        } catch (Exception e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
            return e.getMessage();
        }
    }
}
