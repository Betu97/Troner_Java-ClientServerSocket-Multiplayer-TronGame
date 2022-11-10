package network.requests;

import java.io.Serializable;

/**
 * classe que controla les requests de canvi de direcció provinent dels clients
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class DirectionChangeRequest extends Request implements Serializable {
    private int direction;

    /**
     * Constructor Tournament
     * @param direction
     */
    public DirectionChangeRequest(int direction) {
        this.direction = direction;
    } // tancament constructor

    /**
     * Mètode per obtenir la direcció provinent de la request
     * @return llista d'usuaris
     */
    public int getDirection() {
        return direction;
    } // tancament mètode

    public void setDirection(int direction) {
        this.direction = direction;
    }
} // tancament classe