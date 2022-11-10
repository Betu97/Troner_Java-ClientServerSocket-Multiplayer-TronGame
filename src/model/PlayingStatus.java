package model;

public class PlayingStatus {
    private Round round;
    private int indexInGame;// -1 si no està jugant en cap partida, qualsevol entre 0 i n-1 si està jugant (on n és el num de jugadors)
    private boolean isAlive;
    private int positionInGame;
    private int pointsInGame;
    private int totalPointsInTournament;
    private int wonRounds;

    public PlayingStatus() {
        this.round = null;
        this.indexInGame = -1;// -1 si no està jugant en cap partida, qualsevol entre 0 i n-1 si està jugant (on n és el num de jugadors)
        this.isAlive = true;// Per defecte està viu
        this.positionInGame = 1;// Per defecte definim que sigui el guanyador
        this.pointsInGame = 0;// Per defecte definim 0 punts. Es calcularan els punts en funció del mode de joc
        this.wonRounds = 0;// A l'inici té 0 rondes guanyades (en la sessió actual encara no ha jugat)
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public int getIndexInGame() {
        return indexInGame;
    }

    public void setIndexInGame(int indexInGame) {
        this.indexInGame = indexInGame;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getPositionInRound() {
        return positionInGame;
    }

    public void setPositionInGame(int positionInGame) {
        this.positionInGame = positionInGame;
    }

    public int getPointsInRound() {
        return pointsInGame;
    }

    public void setPointsInGame(int pointsInGame) {
        this.pointsInGame = pointsInGame;
    }

    public int getTotalPointsInTournament() {
        return totalPointsInTournament;
    }

    public void setTotalPointsInTournament(int totalPointsInTournament) {
        this.totalPointsInTournament = totalPointsInTournament;
    }

    public int getWonRounds() {
        return wonRounds;
    }

    public void setWonRounds(int wonRounds) {
        this.wonRounds = wonRounds;
    }
}