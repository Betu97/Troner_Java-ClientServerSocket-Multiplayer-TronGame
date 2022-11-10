package controller.round;

import controller.MainController;
import model.IdentifiedClient;
import model.Round;

public class RoundThread extends Thread {
    private Round round;
    private MainController mainController;

    /**
     * Constructor RoundThread
     * @param round rep una variable round i l'enmagatzema
     */
    public RoundThread(Round round, MainController mainController) {
        this.round = round;
        this.mainController = mainController;
    }

    @Override
    public void run() {
        // Guardem la referència a aquest Thread en el controlador per si l'hem d'aturar
        this.mainController.getGameThreads().put(round, this);

        // En primer lloc, ens guardem en cada client en quina partida estan
        for (IdentifiedClient participant: round.getParticipants()) {
            participant.getPlayingStatus().setRound(round);
        }

        // Esperem a que acabi el compte enrere
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Si en aquest temps d'espera l'usuari ha demanat abandonar, parem el thread
        // Si no continuem
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("S'ha matat. Parem");
            return;
        }
        else {
            System.out.println("No s'ha matat. continuem");
        }

        StartOfRoundNotifier.notifyStart(round);

        outerloop:
        while (!Thread.currentThread().isInterrupted()) {

            // Com que cal moure cada jugador, cal un bucle
            for (IdentifiedClient identifiedClient : this.round.getParticipants()) {
                if (identifiedClient.getPlayingStatus().isAlive()) {
                    // Movem al jugador segons la direcció que tingui en aquest moment
                    boolean gameOver = this.round.move(identifiedClient);

                    if (gameOver) {
                        // identifiedClient ha perdut
                        identifiedClient.getPlayingStatus().setAlive(false);

                        // Cal calcular la seva posició i puntuació per després quan acabi la partida mostrar la seva puntuació
                        int nAliveParticipants = this.round.computeResultOf(identifiedClient);

                        // Cal avisar a tots els participants de que identifiedClient ha perdut
                        PlayerDeathNotifier.notifyGameOverOf(identifiedClient, this.round);

                        // Cal comprovar si el joc ha acabat. Si només queda 1 jugador viu llavors el joc ha acabat
                        if (nAliveParticipants <= 1) {
                            System.out.println("nAliveParticipants: " + nAliveParticipants);
                            // Només queda 1 jugador viu. Per tant, el joc ha acabat
                            // Deixem de moure els jugadors
                            break outerloop;
                        }
                    }
                }
            }

            // Esperem
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Abans de continuar mirem si el Thread es troba interrupted
        // Només continuem en cas de que no es trobi interrupted
        if (!Thread.currentThread().isInterrupted()) {
            // El joc ha acabat
            // Cal avisar d'aquest fet a tots els jugadors
            Round nextRound = EndOfRoundNotifier.notifyGameEnd(round);

            if (nextRound != null) {
                // Cal continuar amb una altra ronda
                // Iniciem el Thread que estarà esperant a que acabi el compte enrere
                RoundThread roundThread = new RoundThread(nextRound, mainController);
                roundThread.start();
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        // A més, aprofitem per a eliminar aquest Thread del HashMap del controlador
        this.mainController.getGameThreads().remove(this.round);
    }
}