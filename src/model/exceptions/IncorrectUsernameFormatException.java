package model.exceptions;

/**
 * classe que llança l'excepció del format erroni de username
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class IncorrectUsernameFormatException extends IncorrectFormatException {

    /**
     * Constructor IncorrectUsernameFormatException
     * @param message
     */
    public IncorrectUsernameFormatException(String message) {
        super(message);
    }
}