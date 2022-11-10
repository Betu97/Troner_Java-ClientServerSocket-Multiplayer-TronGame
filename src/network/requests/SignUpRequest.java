package network.requests;

import network.requests.Request;

import java.io.Serializable;

/**
 * classe que espera una request de tipus Registrar-se
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class SignUpRequest extends Request implements Serializable {
    private String enteredUsername;
    private String enteredEmail;
    private char[] enteredPassword;

    /**
     * Constructor SignUpRequest
     * @param enteredEmail
     * @param enteredPassword
     * @param enteredUsername
     */
    public SignUpRequest(String enteredUsername, String enteredEmail, char[] enteredPassword) {
        this.enteredUsername = enteredUsername;
        this.enteredEmail = enteredEmail;
        this.enteredPassword = enteredPassword;
    } // tancament constructor

    /**
     * Mètode per obtenir el username escrit al field del client
     * @return string amb el valor
     */
    public String getEnteredUsername() {
        return enteredUsername;
    } // tancament mètode

    /**
     * Mètode per obtenir el email escrit al field del client
     * @return string amb el valor
     */
    public String getEnteredEmail() {
        return enteredEmail;
    } // tancament mètode

    /**
     * Mètode per obtenir la password escrita al field del client
     * @return cadena de chars amb el valor
     */
    public char[] getEnteredPassword() {
        return enteredPassword;
    } // tancament mètode
}