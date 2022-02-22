package msprinfra.factory;

import java.time.LocalDateTime;
import java.util.Properties;

public class MailVerification {
    private String code;
    private String mail;
    private LocalDateTime expiration;

    final private Long minuteValidity = 10L;

    public MailVerification() {
        this.makeCode();
        this.expiration = LocalDateTime.now().plusMinutes(minuteValidity);
    }

    /**
     * send the code for 2fa process by mail
     */
    public void sendCodeByMail(){
        // set parameters for the sender
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
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
