package list5.list5_1;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;


public class SMTPFacade {

    public void send(String from, String password, String to, String subject, String body, ByteArrayInputStream attachmentContent, String attachmentMimeType) throws MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject(subject);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(body);
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(messageBodyPart);

        if (attachmentContent != null) {
            MimeBodyPart attachment = new MimeBodyPart();
            DataSource dataSrc = new ByteArrayDataSource(attachmentContent, attachmentMimeType);
            attachment.setDataHandler(new DataHandler(dataSrc));
            attachment.setFileName("test.pdf");
            mp.addBodyPart(attachment);
        }

        message.setContent(mp);

        Transport.send(message);

    }
}
