package network.requests;

import java.io.Serializable;

/**
 * classe que espera una request de tipus newGame
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class NewGameRequest extends Request implements Serializable {
    private int gameType;

    /**
     * Constructor NewGameRequest
     * @param gameType
     */
    public NewGameRequest(int gameType){
        this.gameType = gameType;
    } // tancament constructor

    /**
     * Mètode per obtenir el tipus de round que es vol començar
     * @return enter amb el mètode de joc
     */
    public int getGameType() {
        return gameType;
    } // tancament mètode
} // tancament classe