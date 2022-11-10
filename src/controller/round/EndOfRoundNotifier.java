package controller.round;

import model.IdentifiedClient;
import model.Round;
import model.database.UserDAO;
import network.responses.*;

import java.io.IOException;
import java.sql.SQLException;

public class EndOfRoundNotifier {
    public static Round notifyGameEnd(Round round) {
        boolean isLastRound = false;
        // Si la partida té un torneig associat
        if (round.getAssociatedTournament() != null) {
            // Si estem en l'última ronda de l'última etapa
            if (round.getAssociatedTournament().isLastRound()) {
                isLastRound = true;
            }
        }

        // Preparem el missatge que avança la nova partida
        String message = round.generatePreparationMessage(true);

        // Posem la partida en mode d'espera (per a que no es penalitzi per abandonar-la)
        round.setWaitingMode(true);

        // Avisem a tots els participants de que el joc ha acabat
        for (IdentifiedClient participant : round.getParticipants()) {

            // Si és el guanyador, però, encara no s'ha calculat la seva puntuació. Cal calcular-la
            if (participant.getPlayingStatus().getPositionInRound() == 1) {
                if (round.getParticipants().size() == 4) {
                    participant.getPlayingStatus().setPointsInGame(2);
                    round.getPointsPerParticipant().put(participant, new Integer(2));
                }
                if (round.getParticipants().size() == 3) {
                    participant.getPlayingStatus().setPointsInGame(1);
                    round.getPointsPerParticipant().put(participant, new Integer(1));

                }
                if (round.getParticipants().size() == 2) {
                    participant.getPlayingStatus().setPointsInGame(1);
                    round.getPointsPerParticipant().put(participant, new Integer(1));
                }

                // A més, cal guardar la seva victòria en el comptador de rondes guanyades
                participant.getPlayingStatus().setWonRounds(participant.getPlayingStatus().getWonRounds() + 1);
            }

            if (!isLastRound) {
                // No és l'última ronda
                // Preparem la GameResultResponse
                GameResultResponse gameResultResponse = new GameResultResponse(
                        true,
                        message,
                        participant.getPlayingStatus().getPositionInRound(),
                        participant.getPlayingStatus().getPointsInRound(),
                        false,
                        false,
                        StartOfRoundNotifier.computeNameToLeave(round, round.getAssociatedTournament(), true)// Per a la següent ronda
                );
                // La enviem
                try {
                    participant.getObjectOutputStream().writeObject(gameResultResponse);
                } catch (IOException e) {
                }

                if (round.getAssociatedTournament() == null) {
                    try {
                        System.out.println("primer addScore********** " + participant.getCredentials().getId());
                        UserDAO.addScore(participant.getPlayingStatus().getPointsInRound(), round.getRoundType(), participant.getCredentials());
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                // És l'última ronda del torneig. El torneig ha acabat
                // Preparem la TournamentResultResponse
                TournamentResultResponse tournamentResultResponse = new TournamentResultResponse(
                        true,
                        participant.getPlayingStatus().getPositionInRound(),
                        participant.getPlayingStatus().getPointsInRound(),
                        participant.getPlayingStatus().getTotalPointsInTournament()
                );

                // La enviem
                try {
                    participant.getObjectOutputStream().writeObject(tournamentResultResponse);
                } catch (IOException e) {
                }

                try {
                    System.out.println("2 addScore");
                    UserDAO.addScore(participant.getPlayingStatus().getTotalPointsInTournament(), 3, participant.getCredentials());
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // Si el joc pertany a un torneig pot tenir espectadors. Per tant, cal avisar-los
        if (round.getAssociatedTournament() != null) {
            // Avisem a tots els espectadors de que el joc ha acabat
            for (IdentifiedClient viewer: round.getAssociatedTournament().getViewers()) {
                // Només fem això si l'espectador no és un participant (si no comprovéssim això possiblement ho enviaríem dos cops)
                if (!round.getParticipants().contains(viewer)) {
                    if (!isLastRound) {
                        // Preparem la GameResultResponse
                        GameResultResponse gameResultResponse = new GameResultResponse(
                                true,
                                message,
                                0,
                                0,
                                true,
                                false,
                                "el torneig"
                        );
                        // La enviem
                        try {
                            viewer.getObjectOutputStream().writeObject(gameResultResponse);
                        } catch (IOException e) {
                        }
                    } else {
                        // És l'última ronda del torneig. El torneig ha acabat
                        // Preparem la TournamentResultResponse
                        TournamentResultResponse tournamentResultResponse = new TournamentResultResponse(
                                true,
                                viewer.getPlayingStatus().getPositionInRound(),
                                viewer.getPlayingStatus().getPointsInRound(),
                                viewer.getPlayingStatus().getTotalPointsInTournament()
                        );

                        // La enviem
                        try {
                            viewer.getObjectOutputStream().writeObject(tournamentResultResponse);
                        } catch (IOException e) {
                        }

                        try {
                            System.out.println("3 addScore");
                            UserDAO.addScore(viewer.getPlayingStatus().getTotalPointsInTournament(), 3, viewer.getCredentials());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        if (round.getAssociatedTournament() == null) {
            // Fem un reset a la partida en funció del número de participants que tingui
            switch (round.getParticipants().size()) {
                case 2:
                    round.reset(round.getParticipants().get(0), round.getParticipants().get(1), round.getRoundType());
                    break;
                case 3:
                    round.reset(round.getParticipants().get(0), round.getParticipants().get(1), round.getParticipants().get(2));
                    break;
                case 4:
                    round.reset(round.getParticipants().get(0), round.getParticipants().get(1), round.getParticipants().get(2), round.getParticipants().get(3), round.getRoundType());
                    break;
            }

            // Cal esperar a que acabi el compte enrere
            // Després cal notificar a tots els clients (els que estan esperant) de que comença una partida, a més d'enviar-li l'estat del joc
            // Hem de continuar amb una altra ronda
            return round;
        }
        else if (!round.getAssociatedTournament().isLastRound()) {
            // No estem encara en l'última ronda de l'última etapa...
            IdentifiedClient viewer = round.getAssociatedTournament().prepareNextRound();
            if (viewer != null) {
                // Vol dir que s'ha eliminat a algú. El guardem com a espectador
                round.getAssociatedTournament().getViewers().add(viewer);
            }

            // Cal esperar a que acabi el compte enrere
            // Després cal notificar a tots els clients (els que estan esperant) de que comença una partida, a més d'enviar-li l'estat del joc
            // Hem de continuar amb una altra ronda
            return round.getAssociatedTournament().getCurrentRound();
        }

        // No hem de continuar amb una altra ronda
        return null;
    }
}
