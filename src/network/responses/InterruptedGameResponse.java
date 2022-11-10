package network.responses;

import java.io.Serializable;

public class InterruptedGameResponse extends Response implements Serializable {
    private int gameType;

    public InterruptedGameResponse(boolean successful, int gameType) {
        super(successful);
        this.gameType = gameType;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }
}