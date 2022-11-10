package model.database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.SQLException;

/**
 * classe referent a les dades necesaries pero connectar-nos a la base de dades
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class DBConnector {
    private String host = "127.0.0.1";
    private int port = 3306;
    private String username = "root";
    private String password = "root";
    private String database = "Troner";
    private static Connection connection;

    /**
     * Constructor DBConnector
     * @param host
     * @param port
     * @param username
     * @param password
     * @param database
     */
    public DBConnector (String host, int port, String username, String password, String database){
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    } // tancament constructor

    /**
     * Mètode per començar la connexió amb la base de dades
     */
    public void startConnection() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setServerName(host);
        dataSource.setPort(port);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setDatabaseName(database);

        connection = (Connection) dataSource.getConnection();
    } // tancament mètode

    /**
     * Mètode per obtenir la connexió amb la base de dades
     */
    public static Connection getConnection() {
        return connection;
    } // tancament mètode

    public void endConnection() throws SQLException {
        connection.close();
    }
} // tancament classe