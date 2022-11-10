package network.responses;

import java.io.Serializable;

/**
 * Classe retornada pel servidor per iniciar la compte enrere
 * @author Team Troner (grupC3)
 * @version v_final
 */

public class StartCountdownToQueueResponse extends Response implements Serializable {
    private String message;
    private int position;// De la ronda que ha acabat
    private int points;// De la ronda que ha acabat

    public StartCountdownToQueueResponse(boolean successful, String message, int position, int points) {
        super(successful);
        this.message = message;
        this.position = position;
        this.points = points;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}