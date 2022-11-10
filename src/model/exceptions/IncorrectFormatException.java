package model.exceptions;

/**
 * classe que llança l'excepció del format erroni
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class IncorrectFormatException extends Exception {

    /**
     * Constructor IncorrectEmailFormatException
     * @param message
     */
    public IncorrectFormatException(String message) {
        super(message);
    }
}