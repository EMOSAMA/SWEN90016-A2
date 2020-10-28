package com.shine.faas.admin.service;

import com.shine.faas.admin.orm.RepositoryFactory;
import com.shine.faas.domain.orm.entity.Appointment;
import com.shine.faas.domain.orm.entity.Customer;
import com.shine.faas.domain.orm.entity.Service;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class SendEmail {
    public static String myEmailAccount = "emosamastudio@163.com";
    public static String myEmailPassword = "GFXJVUMQRZMUCQOJ";
    public static String myEmailSMTPHost = "smtp.163.com";

    public static RepositoryFactory repositoryFactory;

    public static void sendEmail(String information, String email, Customer customer, Service service, Appointment appointment) throws Exception {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        String receiveMailAccount = email;
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        Session session = Session.getInstance(props);
        session.setDebug(true);
        MimeMessage message = createMimeMessage(information, customer, service, appointment,session, myEmailAccount, receiveMailAccount);
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public static MimeMessage createMimeMessage(String information, Customer customer, Service service, Appointment appointment, Session session, String sendMail, String receiveMail) throws Exception {
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(sendMail, "Admin", "UTF-8"));

        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "Admin", "UTF-8"));

        message.setSubject(information, "UTF-8");
        String[] times = {"9:00",
                "10:00",
                "11:00",
                "12:00",
                "13:00",
                "14:00",
                "15:00",
                "16:00"};
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        message.setContent("<b>Service Type:</b> " + service.getServiceName() + "<br>"+ "<b>Customer Name:</b> " + customer.getName() + "<br>"+ "<b>Customer Phone:</b> " + customer.getPhone() + "<br>"+ "<b>Customer Email:</b> " + customer.getEmail() + "<br>"+ "<b>Date:</b> " + dateFormat.format(appointment.getDate()) + "<br>"+ "<b>Time:</b> " + times[appointment.getTime()] + "<br>"+ "<b>Message:</b> " + appointment.getMessage(), "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
}