package controller.round;

import controller.MainController;
import controller.tournament.TournamentVictoryNotifier;
import model.IdentifiedClient;
import model.database.UserDAO;
import network.responses.LeaveGameResponse;
import network.responses.StartCountdownToRoundResponse;
import network.responses.TournamentResultResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

public class PlayerDepartureNotifier {
    public static void notifyDepartureOf(IdentifiedClient identifiedClient, boolean doSilently, MainController controller) throws IOException {
        if (identifiedClient.getPlayingStatus().getRound().getAssociatedTournament() == null) {
            // No es tracta d'un torneig

            // Mirem si es tracta del mode 2X o 4X...
            switch (identifiedClient.getPlayingStatus().getRound().getRoundType()) {
                case 1:
                    // Es tracta del mode 2X

                    // En primer lloc contestem a l'usuari que ha demanat abandonar la partida
                    if (identifiedClient.getPlayingStatus().getRound().isWaitingMode()) {
                        // No hi ha penalització
                        if (!doSilently) {
                            identifiedClient.getObjectOutputStream().writeObject(new LeaveGameResponse(true, 0, null));
                        }
                    }
                    else {
                        // Sí hi ha penalització. Penalització de 1 punt en 2X
                        if (!doSilently) {
                            identifiedClient.getObjectOutputStream().writeObject(new LeaveGameResponse(true, 1, null));
                        }
                        try {
                            UserDAO.applyPenalization(1, identifiedClient.getCredentials());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    // Cal avisar a l'altre jugador i posteriorment afegir-lo a la cua
                    LinkedList<IdentifiedClient> players = new LinkedList<>();// Listat dels que hem d'avisar (1 jugador)
                    for (IdentifiedClient participant: identifiedClient.getPlayingStatus().getRound().getParticipants()) {
                        if (participant != identifiedClient) {
                            players.add(participant);
                        }
                    }
                    (new CountdownToQueueThread(players, controller)).start();
                    break;
                case 2:
                    // Es tracta del mode 4X

                    // En primer lloc contestem a l'usuari que ha demanat abandonar la partida
                    if (identifiedClient.getPlayingStatus().getRound().isWaitingMode()) {
                        // No hi ha penalització
                        if (!doSilently) {
                            identifiedClient.getObjectOutputStream().writeObject(new LeaveGameResponse(true, 0, null));
                        }
                    }
                    else {
                        // Sí hi ha penalització. Penalització de 2 punts en 4X
                        if (!doSilently) {
                            identifiedClient.getObjectOutputStream().writeObject(new LeaveGameResponse(true, 2, null));
                        }
                        try {
                            UserDAO.applyPenalization(2, identifiedClient.getCredentials());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    // Cal avisar als altres jugadors i posteriorment afegir-los a la cua
                    for (IdentifiedClient participant: identifiedClient.getPlayingStatus().getRound().getParticipants()) {
                        if (participant != identifiedClient) {
                            // TODO
                        }
                    }
                    break;
            }
        }
        else {
            // Es tracta d'un torneig

            // En primer lloc contestem a l'usuari que ha demanat abandonar la partida
            // Preparem la TournamentResultResponse
            TournamentResultResponse tournamentResultResponse = new TournamentResultResponse(
                    true,
                    identifiedClient.getPlayingStatus().getPositionInRound(),
                    identifiedClient.getPlayingStatus().getPointsInRound(),
                    identifiedClient.getPlayingStatus().getTotalPointsInTournament()
            );
            if (identifiedClient.getPlayingStatus().getRound().getAssociatedTournament().isWaitingMode()) {
                // No hi ha penalització
                if (!doSilently) {
                    identifiedClient.getObjectOutputStream().writeObject(new LeaveGameResponse(true, 0, tournamentResultResponse));
                }
                try {
                    System.out.println("4 addScore");
                    UserDAO.addScore(identifiedClient.getPlayingStatus().getTotalPointsInTournament(), 3, identifiedClient.getCredentials());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                // Sí hi ha penalització. Penalització de 3 punts en torneig
                if (!doSilently) {
                    identifiedClient.getObjectOutputStream().writeObject(new LeaveGameResponse(true, 3, tournamentResultResponse));
                }
                try {
                    System.out.println("5 addScore");
                    UserDAO.addScore(identifiedClient.getPlayingStatus().getTotalPointsInTournament() - 3, 3, identifiedClient.getCredentials());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // La enviem
            try {
                if (!doSilently) {
                    identifiedClient.getObjectOutputStream().writeObject(tournamentResultResponse);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Sigui com sigui cal avisar a la resta de jugadors
            // Cal plantejar-nos si es tracta de l'última etapa o no:
            // - Si ja estem en l'última etapa (2 jugadors) llavors el jugador restant és el guanyador
            // - Si encara no estem en l'última etapa (2 jugadors) llavors cal avançar a la següent etapa sense acabar aquesta
            if (!identifiedClient.getPlayingStatus().getRound().getAssociatedTournament().isLastStage()) {
                System.out.println("encara NO estem en l'última etapa ------------------------------------------------------------------------------------------------------------------------------------------------------");
                // Esborrem la ronda
                controller.getGameThreads().remove(identifiedClient.getPlayingStatus().getRound());
                System.out.println("ronda esborrada. ara hi ha" + controller.getGameThreads().size());

                // Cal mostrar el compte enrere per a la següent ronda
                for (IdentifiedClient participant : identifiedClient.getPlayingStatus().getRound().getParticipants()) {
                    if (participant != identifiedClient) {
                        participant.getObjectOutputStream().writeObject(new StartCountdownToRoundResponse(true, identifiedClient.getCredentials().getUsername() + " ha abandonat el torneig. " +  identifiedClient.getPlayingStatus().getRound().getAssociatedTournament().generatePreparationMessage(true), false, "el torneig"));
                    }
                }

                // Següent etapa
                System.out.println("Següent etapa sense un jugador pq ha abandonat. players: " + identifiedClient.getPlayingStatus().getRound().getAssociatedTournament().getPlayers().size() + ", viewers: " + identifiedClient.getPlayingStatus().getRound().getAssociatedTournament().getViewers().size() + ", participants (es la suma): " + identifiedClient.getPlayingStatus().getRound().getAssociatedTournament().computeParticipants().size());
                identifiedClient.getPlayingStatus().getRound().getAssociatedTournament().prepareNextStageWithout(identifiedClient);

                // Cal esperar a que acabi el compte enrere
                // Després cal notificar a tots els clients (els que estan esperant) de que comença una partida, a més d'enviar-li l'estat del joc
                // Iniciem el Thread que s'encarregarà d'això, i de mantenir viu al joc tot seguit

                RoundThread roundThread = new RoundThread(identifiedClient.getPlayingStatus().getRound().getAssociatedTournament().getCurrentRound(), controller);
                roundThread.start();

                // Guardem la referència per si l'hem d'interrompre
                controller.getGameThreads().put(identifiedClient.getPlayingStatus().getRound().getAssociatedTournament().getCurrentRound(), roundThread);
            }
            else {
                System.out.println("JA estem en l'última etapa ------------------------------------------------------------------------------------------------------------------------------------------------------");
                // Ja estem en l'última etapa (2 jugadors)
                // Per tant, el jugador restant (el que no ha demanat abandonar) és el guanyador
                // Cal avisar només a aquest jugador
                TournamentVictoryNotifier.notifyForcedVictoryAfterDepartureOf(identifiedClient);
            }

            // Finalment eliminem l'usuari com a jugador del torneig
            identifiedClient.getPlayingStatus().getRound().getAssociatedTournament().getPlayers().remove(identifiedClient);
            identifiedClient.getPlayingStatus().getRound().setAssociatedTournament(null);
        }

        // Eliminem del jugador la referència a la partida que es trobava jugant
        identifiedClient.getPlayingStatus().setRound(null);
    }
}
