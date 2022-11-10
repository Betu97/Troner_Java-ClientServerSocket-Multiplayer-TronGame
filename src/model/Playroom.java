package model;

import java.util.LinkedList;

/**
 * classe que porta la lògica dels jugador que estan dins de l'aplicació
 * S'encarrega d'afegir jugador a les cues...
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class Playroom {
    public static final String START_MESSAGE_2X = "Prepara't per a una nova partida 2X...";
    public static final String START_MESSAGE_4X = "Prepara't per a nova partida 4X...";
    private LinkedList<Round> liveRounds;
    private LinkedList<Tournament> liveTournaments;
    private IdentifiedClient waitingFor2X;
    private LinkedList<IdentifiedClient> waitingFor4X;
    private LinkedList<IdentifiedClient> waitingForTournament;

    /**
     * Constructor Playroom
     */
    public Playroom() {
        this.liveRounds = new LinkedList<>();
        this.liveTournaments = new LinkedList<>();
        this.waitingFor4X = new LinkedList<>();
        this.waitingForTournament =  new LinkedList<>();
    } // tancament constructor

    public LinkedList<Round> getLiveRounds() {
        return liveRounds;
    }

    /**
     * Mètode per obtenir l'usuari que està esperant en una cua 2X
     * @return usuari esperant
     */
    public IdentifiedClient getWaitingFor2X() {
        return waitingFor2X;
    } // tancament mètode

    /**
     * Mètode per obtenir els usuaris que està esperant en una cua 4X
     * @return usuaris esperant
     */
    public LinkedList<IdentifiedClient> getWaitingFor4X() {
        return waitingFor4X;
    } // tancament mètode

    /**
     * Mètode per obtenir l'usuari que està esperant en una cua torneig
     * @return usuaris esperant
     */
    public LinkedList<IdentifiedClient> getWaitingForTournament() {
        return waitingForTournament;
    } // tancament mètode

    public void setLiveRounds(LinkedList<Round> liveRounds) {
        this.liveRounds = liveRounds;
    }

    /**
     * Mètode per ficar en espera en 2X a un usuari
     * @param waitingFor2X
     */
    public void setWaitingFor2X(IdentifiedClient waitingFor2X) {
        this.waitingFor2X = waitingFor2X;
    } // tancament mètode

    public void setWaitingFor4X(LinkedList<IdentifiedClient> waitingFor4X) {
        this.waitingFor4X = waitingFor4X;
    }

    public void setWaitingForTournament(LinkedList<IdentifiedClient> waitingForTournament) {
        this.waitingForTournament = waitingForTournament;
    }

    /**
     * Mètode per ficar en una cua 2X a un usuari
     * @param user
     * @return un objecte de tipus Round
     */
    public Round addUserFor2X(IdentifiedClient user) {

        // En primer lloc mirem si amb aquest usuari ja hi ha suficients usuaris (2) com per a començar una ronda 2X
        if (this.waitingFor2X != null) {

            // Comença una partida

            // Com que comença una partida, cal crear els PlayingStatus de cada jugador
            this.waitingFor2X.setPlayingStatus(new PlayingStatus());
            user.setPlayingStatus(new PlayingStatus());

            // A continuació cal assignar un índex a cada jugador (dins del seu PlayingStatus)
            this.waitingFor2X.getPlayingStatus().setIndexInGame(1);
            user.getPlayingStatus().setIndexInGame(2);

            // Ara sí, creem la partida
            Round round = new Round(this.waitingFor2X, user);
            this.liveRounds.add(round);
            this.waitingFor2X = null;
            return round;
        }
        else {
            // Encara no hi ha suficients jugadors per a començar una ronda 2X
            // Per tant simplement afegim l'usuari a la cua del mode 2X
            System.out.println("Sentra a waitingfor2x");
            this.waitingFor2X = user;
            return null;
        }
    }

    /**
     * Mètode per ficar en una cua 4X a un usuari
     * @param user
     * @return un objecte de tipus Round
     */
    public Round addUserFor4X(IdentifiedClient user) {

        // En primer lloc mirem si amb aquest usuari ja hi ha suficients usuaris (4) com per a començar una ronda 4X
        if (this.waitingFor4X.size() == 3) {

            // Comença una partida

            // Com que comença una partida, cal crear els PlayingStatus de cada jugador
            this.waitingFor4X.get(0).setPlayingStatus(new PlayingStatus());
            this.waitingFor4X.get(1).setPlayingStatus(new PlayingStatus());
            this.waitingFor4X.get(2).setPlayingStatus(new PlayingStatus());
            user.setPlayingStatus(new PlayingStatus());

            // A continuació cal assignar un índex a cada jugador (dins del seu PlayingStatus)
            this.waitingFor4X.get(0).getPlayingStatus().setIndexInGame(1);
            this.waitingFor4X.get(1).getPlayingStatus().setIndexInGame(2);
            this.waitingFor4X.get(2).getPlayingStatus().setIndexInGame(3);
            user.getPlayingStatus().setIndexInGame(4);

            // Ara sí, creem la ronda
            Round round = new Round(
                    this.waitingFor4X.get(0),
                    this.waitingFor4X.get(1),
                    this.waitingFor4X.get(2),
                    user
            );
            this.liveRounds.add(round);

            // Buidem la cua del mode 4X
            // The java.util.LinkedList.clear() method removes all of the elements from this list.
            this.waitingFor4X.clear();

            return round;
        }
        else {
            // Encara no hi ha suficients jugadors per a començar una ronda 4X
            // Per tant simplement afegim l'usuari a la cua del mode 4X
            this.waitingFor4X.add(user);
            return null;
        }
    }

    /**
     * Mètode per ficar en un torneig a un usuari
     * @param user
     * @return un objecte de tipus Tournament
     */
    public Tournament addUserForTournament(IdentifiedClient user) {

        // En primer lloc mirem si amb aquest usuari ja hi ha suficients usuaris (4) com per a començar un torneig
        if (this.waitingForTournament.size() == 3) {

            // Comença un torneig

            // Com que comença una partida, cal crear els PlayingStatus de cada jugador
            this.waitingForTournament.get(0).setPlayingStatus(new PlayingStatus());
            this.waitingForTournament.get(1).setPlayingStatus(new PlayingStatus());
            this.waitingForTournament.get(2).setPlayingStatus(new PlayingStatus());
            user.setPlayingStatus(new PlayingStatus());

            // A continuació cal assignar un índex a cada jugador (dins del seu PlayingStatus)
            this.waitingForTournament.get(0).getPlayingStatus().setIndexInGame(1);
            this.waitingForTournament.get(1).getPlayingStatus().setIndexInGame(2);
            this.waitingForTournament.get(2).getPlayingStatus().setIndexInGame(3);
            user.getPlayingStatus().setIndexInGame(4);

            // Ara sí, creem el torneig
            Tournament tournament = new Tournament(
                    this.waitingForTournament.get(0),
                    this.waitingForTournament.get(1),
                    this.waitingForTournament.get(2),
                    user
            );
            this.liveTournaments.add(tournament);

            // Buidem la cua del mode torneig
            // The java.util.LinkedList.clear() method removes all of the elements from this list.
            this.waitingForTournament.clear();

            return tournament;
        }
        else {
            // Encara no hi ha suficients jugadors per a començar un torneig
            // Per tant simplement afegim l'usuari a la cua del mode torneig
            this.waitingForTournament.add(user);
            return null;
        }
    }

    /**
     * Mètode per eliminar d'una cua a un usuari que decideix abandonar
     * @param identifiedClient
     */
    public void removeWaitingUser(IdentifiedClient identifiedClient) {
        // L'esborrem de la cua de 2X
        if (identifiedClient == this.getWaitingFor2X()) {
            this.setWaitingFor2X(null);
        }

        // L'esborrem de la cua de 4X
        this.getWaitingFor4X().remove(identifiedClient);

        // L'esborrem de la cua del torneig
        this.getWaitingForTournament().remove(identifiedClient);
    }
}