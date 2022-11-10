package controller;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * classe encarregada de llegir el fitxer de configuració i d'escriure en ell els canvis que fa l'usuari
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class ConfFileManager {
    private String IP_server_db;
    private int port_client;
    private String database;
    private String user;
    private String password;
    private int port_db;

    /**
     * Constructor de la classe ConfFileManager
     */
    public ConfFileManager() {
    }

    /**
     * Mètode estàtic que carrega la configuració del fitxer JSON configuration
     * @return arrayList de la configuració en format string
     */
    public static ArrayList<String> loadConfiguration() {
        Gson gson = new Gson();
        ConfFileManager config = null;
        try {
            // Convertim la cadena Json a un objecte
            BufferedReader configuration = new BufferedReader(new FileReader("configuration.json"));
            config = gson.fromJson(configuration, ConfFileManager.class );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return convertToString(config);
    }

    /**
     * Mètode estàtic que desa en un objecte ConfFileManager, la informació provinent del mètode loadConfiguartion
     * @return objecte ConfFileManager amb la informació desada
     */
    public static ConfFileManager fromString(ArrayList<String> configString) {
        ConfFileManager config = new ConfFileManager();
        config.IP_server_db = configString.get(0);
        config.port_client = Integer.parseInt(configString.get(1));
        config.database = configString.get(2);
        config.user = configString.get(3);
        config.password = configString.get(4);
        config.port_db = Integer.parseInt(configString.get(5));
        return config;
    }

    public static ArrayList<String> convertToString(ConfFileManager config) {
        ArrayList<String> configuracio = new ArrayList<String>();
        configuracio.add(config.IP_server_db);
        configuracio.add(String.valueOf(config.port_client));
        configuracio.add(config.database);
        configuracio.add(String.valueOf(config.port_db));
        configuracio.add(config.user);
        configuracio.add(config.password);
        return configuracio;
    }

    /**
     * Mètode estàtic que desa la informació en el fitxer JSON configuration
     */
    public static void saveConfiguration(ConfFileManager config) {
        Gson gson = new Gson();
        String json = gson.toJson(config);
        try {
            FileWriter fitxer = new FileWriter("configuration.json");
            fitxer.write(json);
            fitxer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}