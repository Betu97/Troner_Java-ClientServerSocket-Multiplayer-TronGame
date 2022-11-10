package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * classe del client que conté el seus respectius outputStream i inputStream
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class Client {
    protected ObjectOutputStream objectOutputStream;
    protected ObjectInputStream objectInputStream;
    protected Socket sClient;

    /**
     * Constructor Client
     */
    public Client(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, Socket sClient) {
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        this.sClient = sClient;
    }

    /**
     * Mètode que retorna el ObjectOutputStream dessat al constructor
     * @return ObjectOutputStream
     */
    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    } // tancament del mètode

    /**
     * Mètode que retorna el ObjectInputStream dessat al constructor
     * @return ObjectInputStream
     */
    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    } // tancament del mètode

    /**
     * Mètode que retorna si el socket del client esta obert o no
     * @return ObjectInputStream
     */
    public Boolean isClosed(){
        return sClient.isClosed();
    }// tancament del mètode

    /**
     * Procediment que tanca el socket
     * @return ObjectInputStream
     */
    public void close(){
        try{
            sClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// tancament del mètode
} // tancament classe