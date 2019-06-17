package communicate.XMPPConnectionManager;

import org.jivesoftware.smack.AbstractXMPPConnection;

public interface ConnectionManager {
    public AbstractXMPPConnection getConnection();
    public boolean checkConnection();
    public boolean isAuthenticated();
    public void closeConnection();

}
