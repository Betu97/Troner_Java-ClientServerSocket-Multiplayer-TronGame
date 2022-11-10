package network.responses;

import java.io.Serializable;

public class TournamentResultResponse extends Response implements Serializable {
    private int positionInLastRound;
    private int pointsInLastRound;
    private int totalPoints;

    public TournamentResultResponse(boolean successful, int positionInLastRound, int pointsInLastRound, int totalPoints) {
        super(successful);
        this.positionInLastRound = positionInLastRound;
        this.pointsInLastRound = pointsInLastRound;
        this.totalPoints = totalPoints;
    }

    public int getPositionInLastRound() {
        return positionInLastRound;
    }

    public void setPositionInLastRound(int positionInLastRound) {
        this.positionInLastRound = positionInLastRound;
    }

    public int getPointsInLastRound() {
        return pointsInLastRound;
    }

    public void setPointsInLastRound(int pointsInLastRound) {
        this.pointsInLastRound = pointsInLastRound;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}