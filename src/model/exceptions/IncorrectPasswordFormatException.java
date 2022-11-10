package model.exceptions;

/**
 * classe que llança l'excepció del format erroni de password
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class IncorrectPasswordFormatException extends IncorrectFormatException {

    /**
     * Constructor IncorrectEmailFormatException
     * @param message
     */
    public IncorrectPasswordFormatException(String message) {
        super(message);
    }
}