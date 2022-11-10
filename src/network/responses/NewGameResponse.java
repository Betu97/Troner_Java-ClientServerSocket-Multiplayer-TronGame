package network.responses;

import java.io.Serializable;

/**
 * Classe retornada pel servidor quan s'ha enviat la petició d'iniciar una nova partida
 * @author Team Troner (grupC3)
 * @version v_final
 */

public class NewGameResponse extends Response implements Serializable {
    private GameStatusResponse gameStatusResponse;
    private String roundTitle;
    private int[] wonRounds;
    private int indexInGame;
    private boolean startAsViewer;
    private String nameToLeave;

    public NewGameResponse(boolean successful, GameStatusResponse gameStatusResponse, String roundTitle, int[] wonRounds, int indexInGame, boolean startAsViewer, String nameToLeave) {
        super(successful);
        this.gameStatusResponse = gameStatusResponse;
        this.roundTitle = roundTitle;
        this.wonRounds = wonRounds;
        this.indexInGame = indexInGame;
        this.startAsViewer = startAsViewer;
        this.nameToLeave = nameToLeave;
    }

    public GameStatusResponse getGameStatusResponse() {
        return gameStatusResponse;
    }

    public void setGameStatusResponse(GameStatusResponse gameStatusResponse) {
        this.gameStatusResponse = gameStatusResponse;
    }

    public String getRoundTitle() {
        return roundTitle;
    }

    public void setRoundTitle(String roundTitle) {
        this.roundTitle = roundTitle;
    }

    public int[] getWonRounds() {
        return wonRounds;
    }

    public void setWonRounds(int[] wonRounds) {
        this.wonRounds = wonRounds;
    }

    public int getIndexInGame() {
        return indexInGame;
    }

    public void setIndexInGame(int indexInGame) {
        this.indexInGame = indexInGame;
    }

    public boolean isStartAsViewer() {
        return startAsViewer;
    }

    public void setStartAsViewer(boolean startAsViewer) {
        this.startAsViewer = startAsViewer;
    }

    public String getNameToLeave() {
        return nameToLeave;
    }

    public void setNameToLeave(String nameToLeave) {
        this.nameToLeave = nameToLeave;
    }
}