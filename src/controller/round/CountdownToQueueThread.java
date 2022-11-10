package controller.round;

import controller.MainController;
import model.IdentifiedClient;
import model.Playroom;
import model.Round;
import network.responses.StartCountdownToQueueResponse;

import java.io.IOException;
import java.util.LinkedList;

public class CountdownToQueueThread extends Thread {
    private LinkedList<IdentifiedClient> players;
    private MainController mainController;

    public CountdownToQueueThread(LinkedList<IdentifiedClient> players, MainController mainController) {
        this.players = players;
        this.mainController = mainController;
    }

    @Override
    public void run() {
        // Aturem el joc
        this.mainController.getGameThreads().get(this.players.getFirst().getPlayingStatus().getRound()).interrupt();

        // Preparem el missatge
        String message = null;
        int roundType = this.players.getFirst().getPlayingStatus().getRound().getRoundType();
        switch (roundType) {
            case 1:
                message = "No hi ha suficients jugadors per a començar una nova ronda de 2 jugadors. Tornant a la cua del mode 2X...";
                break;
            case 2:
                message = "No hi ha suficients jugadors per a començar una nova ronda de 4 jugadors. Tornant a la cua del mode 4X...";
                break;
        }

        // Mostrem el compte enrere en els clients
        for (IdentifiedClient player: this.players) {
            try {
                player.getObjectOutputStream().writeObject(new StartCountdownToQueueResponse(true, message, player.getPlayingStatus().getPositionInRound(), player.getPlayingStatus().getPointsInRound()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Esperem a que acabi el compte enrere
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Guardem en cada client que ja no estan en cap partida
        for (IdentifiedClient player: this.players) {
            player.getPlayingStatus().setRound(null);
            player.setPlayingStatus(null);
        }

        // Els posem a tots en la cua del mode corresponent
        switch (roundType) {
            case 1:
                for (IdentifiedClient player: this.players) {
                    Round round = this.mainController.getModel().addUserFor2X(player);

                    if (round != null) {
                        // Ha de començar una ronda de 2X
                        String preparationMessage = Playroom.START_MESSAGE_2X;
                        StartOfRoundNotifier.notifyStart(round, null, mainController, preparationMessage);
                    }
                }
                break;
            case 2:
                for (IdentifiedClient player: this.players) {
                    Round round = this.mainController.getModel().addUserFor4X(player);

                    if (round != null) {
                        // Ha de començar una ronda de 4X
                        String preparationMessage = Playroom.START_MESSAGE_4X;
                        StartOfRoundNotifier.notifyStart(round, null, mainController, preparationMessage);
                    }
                }
                break;
        }
    }
}