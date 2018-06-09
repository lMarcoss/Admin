package com.system.mail.service;

import com.system.configuration.AdminProperties;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 08/06/2018
 * Time: 23:40
 */
@Service
public class MailServiceImpl implements MailService {
    private static Logger LOG = Logger.getLogger(MailServiceImpl.class);
    private static String MESSAGE = "Send test message";
    @Autowired
    private AdminProperties adminProperties;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMailSimple(String email) throws Exception {
        MimeMessage message = createMimeMessage(email);
        sendMessage(message);
    }

    private MimeMessage createMimeMessage(String email) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setFrom(adminProperties.getMailFrom());
            mimeMessageHelper.setSubject(MESSAGE);
            mimeMessageHelper.setText(MESSAGE, false);
            return mimeMessage;
        } catch (MessagingException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
            throw new Exception(e.getMessage());
        }
    }

    private void sendMessage(MimeMessage message) throws Exception {
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
            throw new Exception(e.getMessage());
        }

    }
}
