package network.responses;

import java.io.Serializable;

public class LeaveGameResponse extends Response implements Serializable {
    private int penalizationPoints;
    private TournamentResultResponse tournamentResultResponse;

    public LeaveGameResponse(boolean successful, int penalizationPoints, TournamentResultResponse tournamentResultResponse) {
        super(successful);
        this.penalizationPoints = penalizationPoints;
        this.tournamentResultResponse = tournamentResultResponse;
    }

    public int getPenalizationPoints() {
        return penalizationPoints;
    }

    public void setPenalizationPoints(int penalizationPoints) {
        this.penalizationPoints = penalizationPoints;
    }

    public TournamentResultResponse getTournamentResultResponse() {
        return tournamentResultResponse;
    }

    public void setTournamentResultResponse(TournamentResultResponse tournamentResultResponse) {
        this.tournamentResultResponse = tournamentResultResponse;
    }
}