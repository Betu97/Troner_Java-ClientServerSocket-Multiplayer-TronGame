package model.database;

import com.mysql.jdbc.Statement;
import model.Credentials;
import model.exceptions.IncorrectCredentialsException;

import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class UserDAO {
    public static void registerUser(Credentials credentials) throws SQLException {
        try {
            PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement("INSERT INTO User(Nom, Email, Password, LastAccess, Score, DateRegister, Up_key, Down_key, Left_key, Right_key) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, credentials.getUsername());
            preparedStatement.setString(2, credentials.getEmail());
            preparedStatement.setString(3, String.valueOf(credentials.getPassword()));
            preparedStatement.setString(4, credentials.getLastAccess());
            preparedStatement.setInt(5, credentials.getScore());
            preparedStatement.setString(6, credentials.getRegisterDate());
            preparedStatement.setInt(7, KeyEvent.VK_W);
            preparedStatement.setInt(8, KeyEvent.VK_S);
            preparedStatement.setInt(9, KeyEvent.VK_A);
            preparedStatement.setInt(10, KeyEvent.VK_D);

            // Guardem els controls per defecte en credentials
            credentials.setUpKey(KeyEvent.VK_W);
            credentials.setDownKey(KeyEvent.VK_S);
            credentials.setLeftKey(KeyEvent.VK_A);
            credentials.setRightKey(KeyEvent.VK_D);

            // Guardem l'id gràcies a haver indicat Statement.RETURN_GENERATED_KEYS en el moment d'executar el mètode prepareStatement per a generar el PreparedStatement
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                credentials.setId(resultSet.getInt(1));
                System.out.println("l'id de l'usuari registrat es " + credentials.getId());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String checkUsername(Credentials credentials) throws SQLException {
        ResultSet rs = null;
        String nom = null;
        PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement("SELECT Nom FROM User WHERE Nom = ?");
        preparedStatement.setString(1, credentials.getUsername());
        preparedStatement.executeQuery();
        rs = preparedStatement.getResultSet();

        if (rs.next()) {
            nom = rs.getString(1);
        }
        return nom;
    }


    public static String checkEmail(Credentials credentials) throws SQLException {
        ResultSet rs = null;
        String email = null;
        PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement("SELECT Email FROM User WHERE Email = ?");
        preparedStatement.setString(1, credentials.getEmail());
        preparedStatement.executeQuery();
        rs = preparedStatement.getResultSet();

        if (rs.next()) {
            email = rs.getString(1);
        }
        return email;
    }

    public static void logInUserWithUsername(Credentials credentials) throws SQLException, IncorrectCredentialsException {
        // Preparem la sentència
        PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement("SELECT Id, Nom, Up_key, Down_key, Left_key, Right_key FROM User WHERE Nom = ? AND Password = ?");
        preparedStatement.setString(1, credentials.getUsername());
        preparedStatement.setString(2, String.valueOf(credentials.getPassword()));

        // Executem la sentència
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();

        // Capturem el resultat de la sentència
        resultSet.next();
        // Ara que l'usuari ja està loggejat afegim l'id, el username i les keys a Credentials
        credentials.setId(resultSet.getInt(1));
        credentials.setUsername(resultSet.getString(2));
        credentials.setUpKey(resultSet.getInt("Up_key"));
        credentials.setDownKey(resultSet.getInt("Down_key"));
        credentials.setLeftKey(resultSet.getInt("Left_key"));
        credentials.setRightKey(resultSet.getInt("Right_key"));

        // Sortim ja que el login ha sigut satisfactori
        return;
    }

    public static void logInUserWithEmail(Credentials credentials) throws SQLException, IncorrectCredentialsException {
        // Preparem la sentència
        PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement("SELECT Id, Nom, Up_key, Down_key, Left_key, Right_key FROM User WHERE Email = ? AND Password = ?");
        preparedStatement.setString(1, credentials.getEmail());
        preparedStatement.setString(2, String.valueOf(credentials.getPassword()));

        // Executem la sentència
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();

        // Capturem el resultat de la sentència
        resultSet.next();
        // Ara que l'usuari ja està loggejat afegim l'id, el username i les keys a Credentials
        credentials.setId(resultSet.getInt(1));
        credentials.setUsername(resultSet.getString(2));
        credentials.setUpKey(resultSet.getInt("Up_key"));
        credentials.setDownKey(resultSet.getInt("Down_key"));
        credentials.setLeftKey(resultSet.getInt("Left_key"));
        credentials.setRightKey(resultSet.getInt("Right_key"));

        // Sortim ja que el login ha sigut satisfactori
        return;
    }

    public static LinkedList<String[]> getUsersList() throws SQLException {
        LinkedList<String[]> usersList = new LinkedList<String[]>();

        PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement("SELECT Id, Nom, Email, Password, LastAccess, Score, DateRegister FROM User");
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            rs.first();
            while (!rs.isAfterLast()) {
                Credentials c = new Credentials(rs.getString("Nom"), rs.getString("Email"), (rs.getString("Password")).toCharArray(),
                        (rs.getTimestamp("LastAccess")).toString(), rs.getInt("Score"), (rs.getDate("DateRegister")).toString(), rs.getInt("Id"));
                String[] aux = c.toArray();
                usersList.add(aux);
                rs.next();
            }
            rs.close();
        }
        return usersList;
    }


    public static boolean deleteUsers(ArrayList<String> users) throws SQLException {
        if (users.isEmpty()) {
            return false;
        } else {
            for (int i = 0; i < users.size(); i++) {
                ResultSet rs = null;
                PreparedStatement p1 = DBConnector.getConnection().prepareStatement("DELETE FROM game_user WHERE id_user IN (SELECT Id FROM User WHERE Nom = ?);");
                p1.setString(1, users.get(i));
                p1.execute();
                PreparedStatement p3 = DBConnector.getConnection().prepareStatement("DELETE FROM user WHERE Nom = ?;");
                p3.setString(1, users.get(i));
                p3.execute();
            }
            return true;
        }
    }

    public static LinkedList<String[]> getScoreList() throws SQLException {
        LinkedList<String[]> usersList = new LinkedList<String[]>();
        ResultSet rs = null;
        PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement("SELECT Nom, LastAccess, Score FROM User ORDER BY Score DESC");
        rs = preparedStatement.executeQuery();
        if (!rs.next()) {//isEmpty()
        } else {
            rs.first();
            while (!rs.isAfterLast()) {
                String[] aux = new String[3];
                aux[0] = rs.getString("Nom");
                aux[1] = rs.getString("LastAccess");
                aux[2] = rs.getString("Score");
                usersList.add(aux);
                rs.next();
            }
            rs.close();
        }
        return usersList;
    }

    public static String[] getGraphicList() throws SQLException {
        ResultSet rs = null;
        PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement("SELECT Nom FROM User");
        rs = preparedStatement.executeQuery();
        if (!rs.next()) {
            return null;
        } else {
            rs.last();
            String[] usernames = new String[rs.getRow()];
            rs.first();
            int i = 0;
            while (!rs.isAfterLast()) {
                usernames[i] = rs.getString("Nom");
                i++;
                rs.next();
            }
            rs.close();
            return usernames;
        }
    }

    /**
     * Mètode per afegir la puntuació als jugadors
     * @param puntuacion
     * @param gameType
     * @param credentials
     * @throws SQLException
     */

    public static void addScore(int puntuacion, int gameType, Credentials credentials) throws SQLException {

        ResultSet rs = null;
        int id = 0;

        PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement("CALL updateScore(?, ?)");
        preparedStatement.setInt(1, puntuacion);
        preparedStatement.setInt(2, credentials.getId());
        preparedStatement.execute();

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        PreparedStatement preparedStatement2 = DBConnector.getConnection().prepareStatement("INSERT IGNORE INTO Game(Game_type, CreationDate) VALUES (?,?)");
        preparedStatement2.setInt(1, gameType);
        preparedStatement2.setString(2, timeStamp);
        preparedStatement2.execute();

        //nos guardamos el id del juego haciendo uso del timeStamp
        PreparedStatement preparedStatement3 = DBConnector.getConnection().prepareStatement("SELECT Id FROM Game WHERE CreationDate = ?");
        preparedStatement3.setString(1, timeStamp);
        preparedStatement3.execute();
        rs = preparedStatement3.getResultSet();

        //ya tenemos el id del gameType
        if(rs.next()){
            id = rs.getInt(1);
        }

        System.out.println("Este es el id devuelto por la BBDD: " + id);
        System.out.println("ID DEL USUARIO A INSERTAR EN LA BBDD: " + credentials.getId());

        //añadimos a la tabla Game_User
        PreparedStatement preparedStatement4 = DBConnector.getConnection().prepareStatement("INSERT INTO Game_User(id_game, id_user, score) VALUES (?,?,?)");
        preparedStatement4.setInt(1, id);
        preparedStatement4.setInt(2, credentials.getId());
        preparedStatement4.setInt(3, puntuacion);
        preparedStatement4.execute();
    }

    public static void insertGame(int gametype){

    }


    public static LinkedList<String[]> getScore(String user, String gameType) throws SQLException {
        ResultSet rs = null;
        LinkedList<String[]> scoreList = new LinkedList<String[]>();
        PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement("SELECT game_user.Score, " +
                "game.CreationDate FROM game_user, game, user WHERE game_user.id_user = user.Id AND user.Nom = ? " +
                "AND game.game_type = ? AND game.id = game_user.id_game ORDER BY game.CreationDate");
        preparedStatement.setString(1, user);
        preparedStatement.setString(2, gameType);
        rs = preparedStatement.executeQuery();
        if (!rs.next()) {
            //System.out.println("La BBBDD està buida");
            return null;
        } else {
            rs.first();
            while (!rs.isAfterLast()) {
                String[] aux = new String[2];
                aux[0] = rs.getString("Score");
                aux[1] = rs.getString("CreationDate");
                scoreList.add(aux);
                rs.next();
            }
            rs.close();
        }
        return scoreList;
    }

    public static void applyPenalization(int penalization, Credentials credentials) throws SQLException {
        int negativePenalization = penalization * (-1);

        PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement("CALL updateScore(?, ?)");
        preparedStatement.setInt(1, negativePenalization);
        preparedStatement.setInt(2, credentials.getId());
        preparedStatement.execute();
    }

    public static void updateKeys(int desiredUpKey, int desiredDownKey, int desiredLeftKey, int desiredRightKey, Credentials credentials) throws SQLException {
        // Preparem la sentència
        PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement("UPDATE User SET Up_key = ?, Down_key = ?, Left_key = ?, Right_key = ? WHERE user.Id = ?");
        preparedStatement.setInt(1, desiredUpKey);
        preparedStatement.setInt(2, desiredDownKey);
        preparedStatement.setInt(3, desiredLeftKey);
        preparedStatement.setInt(4, desiredRightKey);
        preparedStatement.setInt(5, credentials.getId());

        // Executem la sentència
        preparedStatement.executeUpdate();

        // Guardem les noves tecles (keys) en credentials
        credentials.setUpKey(desiredUpKey);
        credentials.setDownKey(desiredDownKey);
        credentials.setLeftKey(desiredLeftKey);
        credentials.setRightKey(desiredRightKey);
    }
}