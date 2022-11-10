package network;

import controller.MainController;
import model.Client;
import model.Playroom;
import view.MainWindow;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * classe encarregada de ser el servidor del programa
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class Server extends Thread {
    private MainController mainController;
    private Playroom model;
    private MainWindow view;
    private boolean running;
    private int port;
    private ArrayList<DedicatedServer> dedicatedServerList;

    /**
     * Constructor Server
     * @param model
     * @param view
     * @param port
     */
    public Server(MainController mainController, Playroom model, MainWindow view, int port) {
        this.mainController = mainController;
        this.model = model;
        this.view = view;
        this.running = false;
        this.port = port;
        dedicatedServerList = new ArrayList<DedicatedServer>();
    }

    public void startServer () {
        running = true;
        start();
    }
    public void stopServer () {
        running = false;
    }


    /**
     * Mètode pel threat de server
     */
    @Override
    public void run() {
        // Obrim el port
        ServerSocket sServer = null;
        try {
            sServer = new ServerSocket(port);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "ERROR: El port introduït no es troba dins del rang de ports permesos o es troba ocupat. Si us plau, introdueix un altre port en la configuració.", "Troner", JOptionPane.ERROR_MESSAGE);
        }
        try {
            while (running) {
                // Anem acceptant clients de manera indefinida
                Socket sClient = null;
                try {
                    sClient = sServer.accept();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
                System.out.println("NOU CLIENT");

                try {
                    // Preparem els streams de sortida i d'entrada
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(sClient.getOutputStream());
                    ObjectInputStream objectInputStream = new ObjectInputStream(sClient.getInputStream());

                    // Els passem a la informació del client, per utilitzar-los quan calgui
                    Client client = new Client(objectOutputStream, objectInputStream, sClient);

                    // Iniciem el servidor dedicat que s'encarregarà d'atendre a l'usuari
                    DedicatedServer ds = new DedicatedServer(client, mainController, model, view);
                    ds.start();
                    dedicatedServerList.add(ds);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
            //Tanquem el servidor i tots els servidors dedicats
            sServer.close();
            for (DedicatedServer ds : dedicatedServerList){
                ds.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}