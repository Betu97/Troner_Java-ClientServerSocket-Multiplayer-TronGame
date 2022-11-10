package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * classe encarregada de guardar les dades d'un usuari conectat
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class Credentials {
    private int id;// Identificador numèric de l'usuari únic en la base de dades (és clau primària)
    private String username;// El nom d'usuari
    private String email;// El correu electrònic
    private char[] password;// La contrasenya
    private String lastAccess;// La data de l'últim accés
    private int score;// La puntuació acumulada
    private String registerDate;// La data del registre de l'usuari
    private int upKey;
    private int downKey;
    private int leftKey;
    private int rightKey;

    /**
     * Constructor Credentials
     * @param username
     * @param email
     * @param password
     */
    public Credentials(String username, String email, char[] password) {
        this.username = username;
        this.email = email;
        this.password = password;

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        this.lastAccess = reportDate;
        this.score = 0;
        this.registerDate = reportDate;
    }

    /**
     * Constructor Credentials
     * @param username
     * @param email
     * @param password
     * @param lastAccess
     * @param score
     * @param registerDate
     * @param id
     */
    public Credentials(String username, String email, char[] password, String lastAccess, int score, String registerDate, int id) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.lastAccess = lastAccess;
        this.score = score;
        this.registerDate = registerDate;
        this.id = id;
    }

    /**
     * Constructor Credentials
     * @param id
     * @param username
     */
    public Credentials(int id, String username) {
        this.username = username;
        this.id = id;
    }

    /**
     * Mètodes getter i setter de les credencials de l'usuari
     * @return cada un dels mètodes get retorna la seva variable en cuestió
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(String lastAccess) {
        this.lastAccess = lastAccess;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public int getUpKey() {
        return upKey;
    }

    public void setUpKey(int upKey) {
        this.upKey = upKey;
    }

    public int getDownKey() {
        return downKey;
    }

    public void setDownKey(int downKey) {
        this.downKey = downKey;
    }

    public int getLeftKey() {
        return leftKey;
    }

    public void setLeftKey(int leftKey) {
        this.leftKey = leftKey;
    }

    public int getRightKey() {
        return rightKey;
    }

    public void setRightKey(int rightKey) {
        this.rightKey = rightKey;
    }

    /**
     * Mètode que retorna les credencials de l'usuari en forma array de string
     * @return en cada cassella un camp de les credencials de l'usuari
     */
    public String[] toArray(){
        String[] info = new String[8];
        info[0] = Integer.toString(id);
        info[1] = username;
        info[2] = email;
        info[3] = String.copyValueOf(password);
        info[4] = lastAccess;
        info[5] = Integer.toString(score);
        info[6] = registerDate;
        return info;
    }
}