package network.responses;

import java.io.Serializable;

public class GameOverResponse extends Response implements Serializable {
    private int indexInGame;
    private boolean isMe;// Perquè si qui ha perdut és el client a qui li enviem llavors mostrem un missatge

    public GameOverResponse(boolean successful, int indexInGame, boolean isMe) {
        super(successful);
        this.indexInGame = indexInGame;
        this.isMe = isMe;
    }

    public int getIndexInGame() {
        return indexInGame;
    }

    public void setIndexInGame(int indexInGame) {
        this.indexInGame = indexInGame;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }
}