package msprinfra.factory;

import javax.mail.*;
import javax.mail.internet.*;
import java.time.LocalDateTime;
import java.util.Properties;

public class MailVerification {
    private String code;
    final private String senderMail = "";
    final private String mailSenderPassword = "";
    private LocalDateTime expiration;

    final private Long minuteValidity = 10L;

    public MailVerification() {
        this.makeCode();
        this.expiration = LocalDateTime.now().plusMinutes(minuteValidity);
    }

    /**
     * send the code for 2fa process by senderMail
     */
    public void sendCodeByMail(String receiver) throws MessagingException {
        // set parameters for the sender
        Properties prop = new Properties();
        prop.put("senderMail.smtp.auth", true);
        prop.put("senderMail.smtp.starttls.enable", "true");
        prop.put("senderMail.smtp.host", "smtp.gmail.com");
        prop.put("senderMail.smtp.port", "25");
        prop.put("senderMail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderMail, mailSenderPassword);
            }
        });


        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderMail));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(receiver));
        message.setSubject("MSPR login confirmation");

        String msg = "Enter this code for finishing your login: "+code;

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    /**
     * build the code used for 2fa process
     */
    private void makeCode(){

    }

    /**
     * check the time if the code is timeout or not
     * @return boolean
     */
    private boolean checkCodeTimeValidity(){
        return LocalDateTime.now().isBefore(this.expiration);
    }

    /**
     * check if the given code match with the generated
     * @return boolean
     */
    private boolean checkCodeMatch(String code){
        return this.code.equals(code);
    }

    /**
     * check if the given code match with the code set
     * and check the time validity
     * @return boolean
     */
    public boolean verify(String code){
        return checkCodeMatch(code) && checkCodeTimeValidity();
    }

}
