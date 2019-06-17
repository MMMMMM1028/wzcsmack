package communicate.XMPPConnectionManager;

import communicate.configuration.ConstantConfig;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

/**
 * 被装饰的类
 *
 */
public class XMPPConnectionManager implements ConnectionManager {
//    private static ConnectionManager mConnectionManager;
    private AbstractXMPPConnection mConnection;

    public XMPPConnectionManager(){
        getConnection();
    }
//    public static ConnectionManager getInstance(){
//        if (mConnectionManager == null){
//            mConnectionManager = new XMPPConnectionManager();
//        }
//        return mConnectionManager;
//    }

    private void init(){
        XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
//        builder.setResource("Smack");//终端名称，如Smack，Spark等，JID的一部分，可以省略
//        builder.setUsernameAndPassword(myUsername, myPassword);
        builder.setServiceName(ConstantConfig.SERVICE_NAME);
        builder.setHost(ConstantConfig.SEVER_HOST);
        builder.setPort(ConstantConfig.SERVER_PORT);
        builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        builder.setDebuggerEnabled(false);
        XMPPTCPConnectionConfiguration config = builder.build();
        this.mConnection = new XMPPTCPConnection(config);
        try {
            this.mConnection.connect();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }

    public AbstractXMPPConnection getConnection() {

        if (this.mConnection == null){
            //开一个线程建立TCP连接
            Thread conThread = new Thread(new Runnable() {
                public void run() {
                    init();
                }
            });
            conThread.start();
            try {
                //阻塞主线程,等待子线程执行完成
                conThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        return this.mConnection;
    }

    public boolean checkConnection(){
        return mConnection != null && mConnection.isConnected();
    }

    public void closeConnection(){
        if (mConnection != null){
//            connection.removeConnectionListener(connectionListener);
            if (mConnection.isConnected()){
                mConnection.disconnect();
            }
            mConnection = null;
        }
    }

    public boolean isAuthenticated(){
        return mConnection != null && mConnection.isConnected() && mConnection.isAuthenticated();
    }

}
