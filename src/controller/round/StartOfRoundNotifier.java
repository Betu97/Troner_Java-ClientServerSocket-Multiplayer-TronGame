package controller.round;

import controller.MainController;
import model.Round;
import model.RoundStatus;
import model.IdentifiedClient;
import model.Tournament;
import network.responses.GameStatusResponse;
import network.responses.NewGameResponse;
import network.responses.StartCountdownToRoundResponse;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by cristian on 30/6/17.
 */
public class StartOfRoundNotifier {
    public static void notifyStart(Round round) {
        // Desactivem el mode d'espera (llavors es penalitza la retirada)
        round.setWaitingMode(false);

        // Notifiquem a tots els clients (els que estan esperant) de que comença una partida
        // També enviem la matriu inicial (i totes les dades de l'estat del joc)

        // Preparem la info
        RoundStatus roundStatus = round.prepareStatus();

        GameStatusResponse gameStatusResponse = new GameStatusResponse(
                roundStatus.getPositionsMap(),
                roundStatus.getPositions(),
                roundStatus.getDirections()
        );
        String roundTitle = null;
        if (round.getAssociatedTournament() == null) {
            switch (round.getRoundType()) {
                case 1:
                    roundTitle = "Ronda 2X";
                    break;
                case 2:
                    roundTitle = "Ronda 4X";
                    break;
            }
        }
        else {
            roundTitle = round.getAssociatedTournament().generateRoundTitle();
        }
        int[] wonRounds = new int[round.getParticipants().size()];
        for (IdentifiedClient participant: round.getParticipants()) {
            wonRounds[participant.getPlayingStatus().getIndexInGame() - 1] = participant.getPlayingStatus().getWonRounds();
        }

        // Enviem la informació als participants del joc
        for (IdentifiedClient participant: round.getParticipants()) {
            try {
                participant.getObjectOutputStream().writeObject(new NewGameResponse(true, gameStatusResponse, roundTitle, wonRounds, participant.getPlayingStatus().getIndexInGame(), false, StartOfRoundNotifier.computeNameToLeave(round, round.getAssociatedTournament(), false)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Si el joc pertany a un torneig pot tenir espectadors
        if (round.getAssociatedTournament() != null) {
            // Enviem la informació als espectadors del joc que no siguin participants del mateix
            for (IdentifiedClient viewer: round.getAssociatedTournament().getViewers()) {
                if (!round.getParticipants().contains(viewer)) {
                    try {
                        viewer.getObjectOutputStream().writeObject(new NewGameResponse(true, gameStatusResponse, roundTitle, wonRounds, 0, true, "el torneig"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("COMO LO CONTIENE, NO AVISAMOS -----------------------");
                }
            }
        }
    }

    public static void notifyStart(Round round, Tournament tournament, MainController mainController, String preparationMessage) {
        // Ha de començar una ronda / primera ronda d'un torneig

        // Definim la ronda que ha de començar. És una ronda en sí o la primera ronda d'un torneig
        Round firstRound = (round != null) ? round : tournament.getCurrentRound();
        LinkedList<IdentifiedClient> participants = (round != null) ? round.getParticipants() : tournament.computeParticipants();

        // En primer lloc cal mostrar en tots els participants el compte enrere
        // Abans d'enviar la Response per a mostrar en tots els participants el compte enrere cal:
        // Preparar el nom d'allò que podran abandonar: "el torneig" / "el mode 2X" / "el mode 4X"
        String nameToLeave = StartOfRoundNotifier.computeNameToLeave(round, tournament, true);
        for (IdentifiedClient identifiedClient : participants) {
            try {
                identifiedClient.getObjectOutputStream().writeObject(new StartCountdownToRoundResponse(true, preparationMessage, false, nameToLeave));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Com que ha començat una ronda, cal esperar a que acabi el compte enrere
        // Després cal notificar a tots els clients (els que estan esperant) de que comença una partida, a més d'enviar-li l'estat del joc
        // Iniciem el Thread que estarà esperant a que acabi el compte enrere i que mantindrà viu al joc
        RoundThread roundThread = new RoundThread(firstRound, mainController);
        roundThread.start();
    }

    public static String computeNameToLeave(Round round, Tournament tournament, boolean duringCountdown) {
        // Preparem el nom d'allò que podran abandonar: "el torneig" / "el mode 2X" / "el mode 4X"
        String nameToLeave = null;
        if (tournament != null) {
            nameToLeave = "el torneig";
        }
        else if (round != null) {
            if (!duringCountdown) {
                nameToLeave = "la ronda";
            }
            else {
                switch (round.getRoundType()) {
                    case 1:
                        // És mode 2X
                        nameToLeave = "el mode 2X";
                        break;
                    case 2:
                        // És mode 4X
                        nameToLeave = "el mode 4X";
                        break;
                }
            }
        }
        return nameToLeave;
    }
}
