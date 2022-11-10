package model.exceptions;

/**
 * classe que llança l'excepció del format erroni de mail
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class IncorrectEmailFormatException extends IncorrectFormatException {

    /**
     * Constructor IncorrectEmailFormatException
     * @param message
     */
    public IncorrectEmailFormatException(String message) {
        super(message);
    }
}