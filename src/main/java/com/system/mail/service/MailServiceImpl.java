package com.system.mail.service;

import com.system.configuration.AdminProperties;
import com.system.mail.entity.Mail;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
    private static String SUBJECT_LOG_SIES = "Log del sistema sies";
    @Autowired
    private AdminProperties adminProperties;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMailSimple(String email) throws Exception {
        MimeMessage message = createMimeMessage(email);
        sendMessage(message);
    }

    @Override
    public void sendMailWithFile(Mail mail) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        try {
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setFrom(adminProperties.getMailFrom());
            mimeMessageHelper.setSubject(SUBJECT_LOG_SIES);
            mimeMessageHelper.setText(SUBJECT_LOG_SIES, false);

            Multipart multipart = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(mail.getNameFile());
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(obtainNameFile(mail.getNameFile()));
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            sendMessage(message);
        } catch (MessagingException e) {
            throw new Exception(e.getMessage());
        }
    }

    private String obtainNameFile(String nameFile) {
        String[] nameFilePart = nameFile.split("/");
        return nameFilePart[nameFilePart.length - 1];
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
