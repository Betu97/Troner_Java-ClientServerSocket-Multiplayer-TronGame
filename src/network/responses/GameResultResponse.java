package network.responses;

import java.io.Serializable;

/**
 * Classe retornada pel servidor, que indica el seu resultat dins de la partida
 * @author Team Troner (grupC3)
 * @version v_final
 */

public class GameResultResponse extends Response implements Serializable {
    private String message;
    private int position;
    private int points;
    private boolean hasBeenViewer;
    private boolean applicablePenalty;
    private String nameToLeave;

    public GameResultResponse(boolean successful, String message, int position, int points, boolean hasBeenViewer, boolean applicablePenalty, String nameToLeave) {
        super(successful);
        this.message = message;
        this.position = position;
        this.points = points;
        this.hasBeenViewer = hasBeenViewer;
        this.applicablePenalty = applicablePenalty;
        this.nameToLeave = nameToLeave;
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

    public boolean isHasBeenViewer() {
        return hasBeenViewer;
    }

    public void setHasBeenViewer(boolean hasBeenViewer) {
        this.hasBeenViewer = hasBeenViewer;
    }

    public boolean isApplicablePenalty() {
        return applicablePenalty;
    }

    public void setApplicablePenalty(boolean applicablePenalty) {
        this.applicablePenalty = applicablePenalty;
    }

    public String getNameToLeave() {
        return nameToLeave;
    }

    public void setNameToLeave(String nameToLeave) {
        this.nameToLeave = nameToLeave;
    }
}