package communicate;

import communicate.XMPPConnectionManager.ConnectionManager;
import communicate.XMPPConnectionManager.HeartBeatXMPPConnectionManager;
import communicate.XMPPConnectionManager.ReconnectXMPPConnectionManager;

/**
 *  根据配置类的信息，增强connecton的功能，增加消息监听，心跳检测，重连策略
 */

public class XMPPSessionFactory {
    private ConnectionManager connectionManager;
    private XMPPChatManager xmppChatManager;
    private XMPPAccountManager xmppAccountManager;

    public XMPPSessionFactory(ConnectionManager connectionManager, XMPPChatManager xmppChatManager, XMPPAccountManager xmppAccountManager){
        this.connectionManager = connectionManager;
        this.xmppChatManager = xmppChatManager;
        this.xmppAccountManager = xmppAccountManager;
    }

    /**
     * 建立连接，通过装饰器，对连接连接进行增强
     * 制定心跳检测，保持长连接
     * 制定重连策略，
     * @return
     */
    public XMPPSession openSession(){
        ConnectionManager connectionManager = new HeartBeatXMPPConnectionManager(new ReconnectXMPPConnectionManager(this.connectionManager));
        return new XMPPSessionImpl(connectionManager,this.xmppChatManager,this.xmppAccountManager);

    }
}
