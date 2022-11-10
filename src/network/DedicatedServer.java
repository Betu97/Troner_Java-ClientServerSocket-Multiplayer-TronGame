package network;

import controller.MainController;
import controller.round.PlayerDepartureNotifier;
import controller.round.StartOfRoundNotifier;
import model.*;
import model.database.UserDAO;
import network.requests.*;
import network.responses.*;
import view.MainWindow;
import model.exceptions.RepeatedKeysException;

import java.io.EOFException;
import java.io.IOException;
import java.sql.SQLException;

public class DedicatedServer extends Thread {
    private MainController mainController;
    private Playroom model;
    private MainWindow view;
    private boolean running;

    // Informació del client
    private Client client;

    public DedicatedServer(Client client, MainController mainController, Playroom model, MainWindow view) {
        this.client = client;
        this.mainController = mainController;
        this.model = model;
        this.view = view;
        running = true;
    }

    public void close(){
        running = false;
    }

    @Override
    public void run() {
        try {
            while (running) {
                System.out.println("encara no és una petició");
                // Obtenim la informació provinent de l'usuari en forma de Request (petició)
                Request request = (Request)this.client.getObjectInputStream().readObject();
                System.out.println("és una petició");

                // Mirem si la petició és una petició de registrar-se
                if (request instanceof SignUpRequest) {
                    SignUpRequest signUpRequest = (SignUpRequest)request;
                    System.out.println("és una petició de registrar-se");

                    // Comprovem si el format del nom d'usuari, de l'email i de la contrasenya és el correcte
                    if (
                            FormValidator.validateUsername(signUpRequest.getEnteredUsername()) &&
                            FormValidator.validateEmail(signUpRequest.getEnteredEmail()) &&
                            FormValidator.validatePassword(String.copyValueOf(signUpRequest.getEnteredPassword()))
                    ) {
                        // El format del nom d'usuari, de l'email i de la contrasenya és el correcte

                        // Creem l'objecte Credentials a partir del nom d'usuari, de l'email i de la contrasenya
                        Credentials credentials = new Credentials(signUpRequest.getEnteredUsername(), signUpRequest.getEnteredEmail(), signUpRequest.getEnteredPassword());

                        // Abans de fer persistir aquesta informació en la base de dades, cal fer dues comprovacions:
                        // 1. Comprovar si ja existeix el nom d'usuari
                        // 2. Comprovem si ja existeix l'email
                        System.out.println("Ara farem persistir l'usuari en la base de dades");

                        String existingUsername = null;// Valdrà null si no existeix el nom d'usuari
                        String existingEmail = null;// Valdrà null si no existeix l'email

                        // Comprovem si el nom d'usuari ja existeix a la BD
                        try {
                            existingUsername = UserDAO.checkUsername(credentials);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        // Comprovem si l'email ja existeix a la BD
                        try {
                            existingEmail = UserDAO.checkEmail(credentials);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        if (existingUsername != null) {
                            // El nom d'usuari ja existeix a la BD
                            // Per aquest motiu, enviem un sign up fallat
                            this.client.getObjectOutputStream().writeObject(new SignUpResponse(false, credentials.getUsername(), 1, 0, 0, 0, 0));
                        }
                        else if (existingEmail != null) {
                            // L'email ja existeix a la BD
                            // Per aquest motiu, enviem un sign up fallat
                            this.client.getObjectOutputStream().writeObject(new SignUpResponse(false, credentials.getEmail(), 2, 0, 0, 0, 0));
                        }
                        else {
                            // Ni el nom d'usuari ni l'email existeixen encara en la BD
                            try {
                                UserDAO.registerUser(credentials);
                                // Guardem la informació en this.client
                                this.client = new IdentifiedClient(this.client, credentials);
                                // Enviem al client un sign up amb èxit
                                this.client.getObjectOutputStream().writeObject(new SignUpResponse(true, credentials.getUsername(), 0, credentials.getUpKey(), credentials.getDownKey(), credentials.getLeftKey(), credentials.getRightKey()));

                            } catch (SQLException e) {
                                // No s'ha pogut guardar a la base de dades
                                // Enviem al client un sign up fallat
                                this.client.getObjectOutputStream().writeObject(new SignUpResponse(false, credentials.getUsername(), 0, 0, 0, 0, 0));
                            }
                        }
                    }
                    else {
                        // El format del nom d'usuari, de l'email o de la contrasenya NO és el correcte
                        // Enviem al client un sign up fallat
                        this.client.getObjectOutputStream().writeObject(new SignUpResponse(false, null, 0, 0, 0, 0, 0));
                    }
                }

                // Mirem si la petició és una petició de log in
                if (request instanceof LogInRequest) {
                    LogInRequest logInRequest = (LogInRequest)request;
                    System.out.println("Es una peticion de loggear-se");
                    System.out.println(logInRequest.getEnteredUsernameOrEmail());

                    // Comprovem si el format del nom d'usuari / de l'email i de la contrasenya és el correcte
                    if ((FormValidator.validateEmail(logInRequest.getEnteredUsernameOrEmail())) && FormValidator.validatePassword(String.copyValueOf(logInRequest.getEnteredPassword()))) {

                        // El format de l'email i de la contrasenya és el correcte
                        System.out.println("entramos por email");

                        // Creem l'objecte Credentials a partir de l'email i de la contrasenya
                        Credentials credentials = new Credentials(null, logInRequest.getEnteredUsernameOrEmail(), logInRequest.getEnteredPassword());

                        // Fem el log in amb la base de dades
                        try {
                            // Gràcies a cridar a aquesta funció, ara credentials tindrà l'id i el username correcte (en cas de que el log in hagi sigut correcte)
                            UserDAO.logInUserWithEmail(credentials);

                            // Guardem la informació en this.client
                            this.client = new IdentifiedClient(this.client, credentials);

                            // Enviem al client un log in amb èxit
                            this.client.getObjectOutputStream().writeObject(new LogInResponse(true, credentials.getUsername(), credentials.getUpKey(), credentials.getDownKey(), credentials.getLeftKey(), credentials.getRightKey()));

                        } catch (SQLException e) {
                            // No s'ha pogut fer log in amb la base de dades ja que les credencials són incorrectes
                            // Enviem al client un log in fallat
                            this.client.getObjectOutputStream().writeObject(new LogInResponse(false, null, 0, 0, 0, 0));
                        }
                    }
                    else if ((FormValidator.validateUsername(logInRequest.getEnteredUsernameOrEmail()))) {

                        // El format del nom d'usuari i de la contrasenya és el correcte
                        System.out.println("entramos por username");

                        // Creem l'objecte Credentials a partir de l'email i de la contrasenya
                        Credentials credentials = new Credentials(logInRequest.getEnteredUsernameOrEmail(), null, logInRequest.getEnteredPassword());

                        // Fem el log in amb la base de dades
                        try {
                            // Gràcies a cridar a aquesta funció, ara credentials tindrà l'id i el username correcte (en cas de que el log in hagi sigut correcte)
                            UserDAO.logInUserWithUsername(credentials);

                            // Guardem la informació en this.client
                            this.client = new IdentifiedClient(this.client, credentials);

                            // Enviem al client un log in amb èxit
                            this.client.getObjectOutputStream().writeObject(new LogInResponse(true, credentials.getUsername(), credentials.getUpKey(), credentials.getDownKey(), credentials.getLeftKey(), credentials.getRightKey()));

                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                            // No s'ha pogut fer log in amb la base de dades ja que les credencials són incorrectes
                            // Enviem al client un log in fallat
                            this.client.getObjectOutputStream().writeObject(new LogInResponse(false, null, 0, 0, 0, 0));
                        }
                    }
                    else {
                        // Per aquest motiu, enviem al client un log in fallat
                        // El format del nom d'usuari / de l'email i de la contrasenya NO és el correcte
                        this.client.getObjectOutputStream().writeObject(new LogInResponse(false, null, 0, 0, 0, 0));
                    }
                }

                // Mirem si la petició és una petició de log out
                if (request instanceof LogOutRequest) {
                    // Mirem si, efectivament, l'usuari està loggejat
                    if (this.client instanceof IdentifiedClient) {
                        // Efectivament, l'usuari està loggejat

                        // L'esborrem de les cues d'espera
                        this.model.removeWaitingUser((IdentifiedClient) this.client);

                        // Ara sí, fem log out
                        this.client = ((IdentifiedClient) this.client).logOut();

                        // Notifiquem a l'usuari de que s'ha fet el log out amb èxit
                        this.client.getObjectOutputStream().writeObject(new LogOutResponse(true));
                    }
                    else {
                        // L'usuari no està loggejat
                        // Notifiquem a l'usuari de que no s'ha pogut fer el log out
                        this.client.getObjectOutputStream().writeObject(new LogOutResponse(false));
                    }
                }

                // Mirem si la petició és una petició de actualitzacio de ranking
                if (request instanceof RankingClientRequest) {
                    System.out.println("Actualitza ranking");

                    try{
                        this.client.getObjectOutputStream().writeObject(new RankingClientResponse(true, UserDAO.getScoreList()));
                    } catch (SQLException e){
                        System.out.println("Error al agafar el ranking");
                        this.client.getObjectOutputStream().writeObject(new RankingClientResponse(false, null));
                    }
                    view.refreshRankingList(UserDAO.getScoreList());
                }

                // Mirem si la petició és d'actualizació de les tecles (els controls)
                if (request instanceof UpdateKeysRequest) {
                    UpdateKeysRequest updateKeysRequest = (UpdateKeysRequest)request;
                    int up = updateKeysRequest.getDesiredUpKey();
                    int down = updateKeysRequest.getDesiredDownKey();
                    int left = updateKeysRequest.getDesiredLeftKey();
                    int right = updateKeysRequest.getDesiredRightKey();
                    try {
                        if (
                            up == down || up == left || up == right ||
                            down == left || down == right ||
                            left == right
                        ) {
                            throw new RepeatedKeysException();
                        }
                        else {
                            // Provem d'actualitzar les tecles a la base de dades (si no es pot fer llança excepció)
                            UserDAO.updateKeys(up, down, left, right, ((IdentifiedClient) this.client).getCredentials());

                            // Contestem al client: les tecles s'han actualitzat correctament (true)
                            this.client.getObjectOutputStream().writeObject(new UpdatedKeysResponse(true, up, down, left, right));
                        }
                    }
                    catch (SQLException | RepeatedKeysException e) {
                        // Contestem al client: les tecles NO s'han actualitzat correctament (false)
                        this.client.getObjectOutputStream().writeObject(new UpdatedKeysResponse(false, updateKeysRequest.getDesiredUpKey(), updateKeysRequest.getDesiredDownKey(), updateKeysRequest.getDesiredLeftKey(), updateKeysRequest.getDesiredRightKey()));
                    }
                }

                // Mirem si la petició és una petició de partida
                if (request instanceof NewGameRequest) {
                    NewGameRequest newGameRequest = (NewGameRequest)request;
                    System.out.println("Es una petició de partida");

                    Round round = null;
                    Tournament tournament = null;
                    String preparationMessage = null;

                    switch (newGameRequest.getGameType()) {
                        case 1:
                            // Partida 2X
                            round = this.model.addUserFor2X((IdentifiedClient) this.client);

                            if (round != null) {
                                // Ha de començar una ronda de 2X
                                preparationMessage = Playroom.START_MESSAGE_2X;
                            }
                            break;
                        case 2:
                            // Partida 4X
                            round = this.model.addUserFor4X((IdentifiedClient) this.client);

                            if (round != null) {
                                // Ha de començar una ronda de 4X
                                preparationMessage = Playroom.START_MESSAGE_4X;
                            }
                            break;
                        case 3:
                            // Torneig
                            tournament = this.model.addUserForTournament((IdentifiedClient) this.client);

                            if (tournament != null) {
                                // Ha de començar un torneig
                                preparationMessage = tournament.generatePreparationMessage(false);
                            }
                            break;
                    }

                    if (round != null || tournament != null) {
                        StartOfRoundNotifier.notifyStart(round, tournament, mainController, preparationMessage);
                    }
                }

                // Mirem si la petició és una petició de deixar d'esperar (per a començar un joc)
                if (request instanceof StopWaitingRequest) {
                    // L'esborrem de les cues d'espera
                    this.model.removeWaitingUser((IdentifiedClient) this.client);
                    // Notifiquem a l'usuari
                    this.client.getObjectOutputStream().writeObject(new StopWaitingResponse(true));
                }

                // Mirem si la petició és una petició de canvi de direcció
                if (request instanceof DirectionChangeRequest) {
                    DirectionChangeRequest directionChangeRequest = (DirectionChangeRequest)request;

                    boolean updatedDirection = false;

                    // Actualitzem la direcció en el model del servidor sii (si i només si) es tracta d'un gir de 90 graus
                    switch (((IdentifiedClient) this.client).getPlayingStatus().getRound().getDirections().get((IdentifiedClient) this.client)) {
                        case 0:
                            // La seva direcció actual és RIGHT
                            // Per tant, només acceptem les noves direccions: UP o DOWN
                            // És a dir, 2 o 3
                            if (directionChangeRequest.getDirection() == 2 || directionChangeRequest.getDirection() == 3) {
                                ((IdentifiedClient) this.client).getPlayingStatus().getRound().changeDirection((IdentifiedClient) this.client, directionChangeRequest.getDirection());
                            }
                            updatedDirection = true;
                            break;
                        case 1:
                            // La seva direcció actual és LEFT
                            // Per tant, només acceptem les noves direccions: UP o DOWN
                            // És a dir, 2 o 3
                            if (directionChangeRequest.getDirection() == 2 || directionChangeRequest.getDirection() == 3) {
                                ((IdentifiedClient) this.client).getPlayingStatus().getRound().changeDirection((IdentifiedClient) this.client, directionChangeRequest.getDirection());
                            }
                            updatedDirection = true;
                            break;
                        case 2:
                            // La seva direcció actual és UP
                            // Per tant, només acceptem les noves direccions: RIGHT o LEFT
                            // És a dir, 0 o 1
                            if (directionChangeRequest.getDirection() == 0 || directionChangeRequest.getDirection() == 1) {
                                ((IdentifiedClient) this.client).getPlayingStatus().getRound().changeDirection((IdentifiedClient) this.client, directionChangeRequest.getDirection());
                            }
                            updatedDirection = true;
                            break;
                        case 3:
                            // La seva direcció actual és DOWN
                            // Per tant, només acceptem les noves direccions: RIGHT o LEFT
                            // És a dir, 0 o 1
                            if (directionChangeRequest.getDirection() == 0 || directionChangeRequest.getDirection() == 1) {
                                ((IdentifiedClient) this.client).getPlayingStatus().getRound().changeDirection((IdentifiedClient) this.client, directionChangeRequest.getDirection());
                            }
                            updatedDirection = true;
                            break;
                    }

                    if (updatedDirection) {
                        // Enviem la nova matriu (i totes les dades de l'estat del joc) a tots els participants
                        // Preparem la info
                        RoundStatus roundStatus = ((IdentifiedClient) this.client).getPlayingStatus().getRound().prepareStatus();

                        GameStatusResponse gameStatusResponse = new GameStatusResponse(
                                roundStatus.getPositionsMap()/*this.identifiedClient.getRound().getPositionsMap()*/,
                                roundStatus.getPositions(),
                                roundStatus.getDirections()
                        );
                        // La enviem
                        for (IdentifiedClient identifiedClient : ((IdentifiedClient) this.client).getPlayingStatus().getRound().getParticipants()) {
                            try {
                                identifiedClient.getObjectOutputStream().writeObject(gameStatusResponse);
                            } catch (IOException e) {
                            }
                        }

                        // Si el joc pertany a un torneig pot tenir espectadors
                        if (((IdentifiedClient) this.client).getPlayingStatus().getRound().getAssociatedTournament() != null) {
                            // Enviem la informació als espectadors del joc que no siguin participants del mateix
                            for (IdentifiedClient viewer: ((IdentifiedClient) this.client).getPlayingStatus().getRound().getAssociatedTournament().getViewers()) {
                                if (!((IdentifiedClient) this.client).getPlayingStatus().getRound().getParticipants().contains(viewer)) {
                                    try {
                                        viewer.getObjectOutputStream().writeObject(gameStatusResponse);
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            }
                        }

                    }
                }

                // Mirem si és una petició d'abandonar la partida
                if (request instanceof LeaveGameRequest) {
                    System.out.println("leaveGame. Matem el Thread que manté viu al joc");
                    // Matem el Thread que manté viu al joc
                    if (this.mainController.getGameThreads().get(((IdentifiedClient) this.client).getPlayingStatus().getRound()) != null && this.mainController.getGameThreads().get(((IdentifiedClient) this.client).getPlayingStatus().getRound()).isAlive()) {
                        this.mainController.getGameThreads().get(((IdentifiedClient) this.client).getPlayingStatus().getRound()).interrupt();
                    }
                    // Notifiquem de l'abandonament de l'usuari del joc en què es troba jugant
                    PlayerDepartureNotifier.notifyDepartureOf((IdentifiedClient) this.client, false, this.mainController);
                }
            }
        }
        catch (EOFException e) {
            // L'usuari ha desconnectat el programa

            // Si es troba en un joc cal notificar-ho
            if (this.client instanceof IdentifiedClient && ((IdentifiedClient) this.client).getPlayingStatus() != null && ((IdentifiedClient) this.client).getPlayingStatus().getRound() != null) {
                try {
                    System.out.println("leaveGame. Matem el Thread que manté viu al joc");
                    // Matem el Thread que manté viu al joc
                    if (this.mainController.getGameThreads().get(((IdentifiedClient) this.client).getPlayingStatus().getRound()) != null && this.mainController.getGameThreads().get(((IdentifiedClient) this.client).getPlayingStatus().getRound()).isAlive()) {
                        this.mainController.getGameThreads().get(((IdentifiedClient) this.client).getPlayingStatus().getRound()).interrupt();
                    }
                    // Notifiquem de l'abandonament de l'usuari del joc en què es troba jugant
                    PlayerDepartureNotifier.notifyDepartureOf((IdentifiedClient) this.client, true, this.mainController);
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(!client.isClosed()){
                client.close();
            }
        }
    }
}