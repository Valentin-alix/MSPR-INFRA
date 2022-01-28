package msprinfra.factory;

import msprinfra.model.Utilisateur;

public class Token {
    /**
     * token used in the session
     * this token is valid 2h
     */
    String token;
    String expirationDatetime;

    public Token(String token) {
        this.token = token;
    }

    /**
     * generate token for 2fa for the user
     *
     * @return String
     */
    private String generateToken(){
        String token = "";
        return token;
    }

    /**
     * check if the token give by the user is correct
     *
     * @param token token to check
     * @return boolean
     */
    public static boolean checkToken(String token){
        return true;
    }

}
