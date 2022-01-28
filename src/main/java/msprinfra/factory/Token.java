package msprinfra.factory;

import java.time.LocalDateTime;

public class Token {

    /**
     * token duration validity in hours
     */
    private Long tokenDurationValidity = 2L;

    /**
     * token used in the session
     * this token is valid 2h
     */
    private final String token;


    /**
     * stock the expiration datetime
     * the expiration is 2h after the
     * token generation
     */
    private LocalDateTime expirationDateTime;

    public Token() {
        this.token = this.generateToken();
    }

    /**
     * generate token for 2fa for the user
     *
     * @return String
     */
    private String generateToken(){
        String token = "";
        this.expirationDateTime = LocalDateTime.now().plusHours(this.tokenDurationValidity);
        return token;
    }

    /**
     * check if the token give by the user is correct
     *
     * @return boolean
     */
    public boolean checkTokenValidity(){
        return LocalDateTime.now().isBefore(this.expirationDateTime);
    }

    public String getToken() {
        return token;
    }
}
