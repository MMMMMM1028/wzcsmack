package communicate;

import communicate.XMPPConnectionManager.ConnectionManager;
import communicate.XMPPConnectionManager.XMPPConnectionManager;

import java.sql.Connection;

/**
 * 从常量类中读取信息构建XMPPSessionFactory工厂
 * bulid函数创建工厂
 *      解析配置类
 *      build communicate.XMPPAccountManager
 *      build MyChaManager
 *      build MyXMPPConnectionManager
 *
 *
 */
public class XMPPSessionFactoryBuilder {
    /**
     * build函数，组装各个管理类
     * @return
     */
    public XMPPSessionFactory build(){
        XMPPConnectionManager xmppConnectionManager = buildConnectionManager();
        XMPPChatManager xmppChatManager = buildChatManager(xmppConnectionManager);
        XMPPAccountManager xmppAccountManager = bulidAccountManager(xmppConnectionManager);

        return new XMPPSessionFactory(xmppConnectionManager,xmppChatManager,xmppAccountManager);

    }

    private XMPPConnectionManager buildConnectionManager(){
        return new XMPPConnectionManager();
    }
    private XMPPChatManager buildChatManager(ConnectionManager connectionManager){
        return XMPPChatManager.getInstance(connectionManager);
    }
    private XMPPAccountManager bulidAccountManager(ConnectionManager connectionManager){
        return XMPPAccountManager.getInstance(connectionManager);
    }

}
