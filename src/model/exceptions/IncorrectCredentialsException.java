package model.exceptions;


/**
 * classe que llença l'excepció relacionada amb les credencials
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class IncorrectCredentialsException extends Exception {

    /**
     * Constructor IncorrectCredentialsException
     * @param message
     */
     public IncorrectCredentialsException(String message) {
        super(message);
    }
}