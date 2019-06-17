package communicate.XMPPConnectionManager;

import org.jivesoftware.smack.AbstractXMPPConnection;

public abstract class AbstractXMPPConnectionManager implements ConnectionManager {
    protected ConnectionManager connectionManager;
//    private AbstractXMPPConnection mConnection;
    public AbstractXMPPConnectionManager(ConnectionManager connectionManager){
        this.connectionManager = connectionManager;
    }

    public AbstractXMPPConnection getConnection() {
        return connectionManager.getConnection();
    }

    public boolean checkConnection() {
        return connectionManager.checkConnection();
    }

    public boolean isAuthenticated() {
        return connectionManager.isAuthenticated();
    }

    public void closeConnection() {
        connectionManager.closeConnection();
    }

}
