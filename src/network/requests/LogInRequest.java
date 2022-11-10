package network.requests;

import java.io.Serializable;

/**
 * classe que espera una request de tipus logIn provinent del client
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class LogInRequest extends Request implements Serializable {

    private String enteredUsernameOrEmail;
    private char[] enteredPassword;

    /**
     * Constructor Tournament
     * @param enteredPassword
     * @param enteredUsername
     */
    public LogInRequest(String enteredUsername, char[] enteredPassword) {
        this.enteredUsernameOrEmail = enteredUsername;
        this.enteredPassword = enteredPassword;
    } // tancament constructor

    /**
     * Mètode per obtenir les dades escrites en el camp del LogIn del client
     * @return llista d'usuaris
     */
    public String getEnteredUsernameOrEmail() {
        return enteredUsernameOrEmail;
    } // tancament mètode

    public char[] getEnteredPassword() {
        return enteredPassword;
    }
}
