package com.system.mail.controller;

import com.system.mail.service.MailService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 09/06/2018
 * Time: 00:19
 */
@Controller
@RequestMapping("/mail")
public class MailController {
    private static Logger LOG = Logger.getLogger(MailController.class);
    private static String MESSAGE_SENT = "Message sent";

    @Autowired
    private MailService mailService;

    @GetMapping("/sendMail/{email}")
    public String sendMail(@PathVariable String email) {
        try {
            mailService.sendMailSimple("leonardo.marcos@meltsan.com");
            return MESSAGE_SENT;
        } catch (Exception e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
            return e.getMessage();
        }
    }
}
