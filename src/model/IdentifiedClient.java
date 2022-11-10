package model;

/**
 * classe encarregada de desar l'usuari que es troba logejat
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class IdentifiedClient extends Client {
    private Credentials credentials;
    private PlayingStatus playingStatus;
    private String[] keys;

    /**
     * Constructor IdentifiedClient
     * @param credentials
     * @param client
     */
    public IdentifiedClient(Client client, Credentials credentials) {
        super(client.objectOutputStream, client.objectInputStream, client.sClient);
        this.credentials = credentials;
        this.playingStatus = null;// Per defecte establim null perquè inicialment no es troba jugant en cap ronda
        // TODO this.keys = ...
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public PlayingStatus getPlayingStatus() {
        return playingStatus;
    }

    public void setPlayingStatus(PlayingStatus playingStatus) {
        this.playingStatus = playingStatus;
    }

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    public Client logOut() {
        return new Client(this.objectOutputStream, this.objectInputStream, this.sClient);
    }
}