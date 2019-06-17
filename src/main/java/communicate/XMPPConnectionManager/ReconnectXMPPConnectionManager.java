package communicate.XMPPConnectionManager;

import communicate.configuration.ConstantConfig;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ReconnectionManager;

/**
 * 装饰类，制定重连策略
 */
public class ReconnectXMPPConnectionManager extends AbstractXMPPConnectionManager {
    public ReconnectXMPPConnectionManager(ConnectionManager abstractXMPPConManager){
        super(abstractXMPPConManager);
        AbstractXMPPConnection mConnection =  abstractXMPPConManager.getConnection();
        addReconnectionManager(mConnection);
    }

//    @Override
//    public AbstractXMPPConnection getConnection() {
//        AbstractXMPPConnection mConnection =  super.getConnection();
//        addReconnectionManager(mConnection);
//        return mConnection;
//    }

    /**
     * 重连策略，如果链接断开则随机时间重新链接
     *
     * @param mConnection
     */
    private void addReconnectionManager(final AbstractXMPPConnection mConnection){
        ReconnectionManager reconnectionManager = ReconnectionManager.getInstanceFor(mConnection);
        //设置重连策略
        reconnectionManager.setReconnectionPolicy(ConstantConfig.DEFAULT_RECONNECTION_POLICY);
        //开启自动重连
        reconnectionManager.enableAutomaticReconnection();
        ReconnectionManager.setEnabledPerDefault(ConstantConfig.AUTOMICA_RECONNECT_ENABLED);
    }
}
