package communicate.XMPPConnectionManager;

import communicate.configuration.ConstantConfig;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.ping.PingFailedListener;
import org.jivesoftware.smackx.ping.PingManager;

import java.io.IOException;

/**
 * 装饰类，制定发送心跳策略
 */
public class HeartBeatXMPPConnectionManager extends AbstractXMPPConnectionManager {
    public HeartBeatXMPPConnectionManager(ConnectionManager abstractXMPPConManager){
        super(abstractXMPPConManager);
        AbstractXMPPConnection mConnection = abstractXMPPConManager.getConnection();
        addPingManager(mConnection);
    }

//    @Override
//    public AbstractXMPPConnection getConnection() {
////        AbstractXMPPConnection mConnection = super.getConnection();
////        //增强一下功能
////        addPingManager(mConnection);
//        AbstractXMPPConnection mConnection = this.abstractXmppConManager.getConnection();
//        return mConnection;
//    }

    /**
     * 发送心跳
     * 主要目的保持TCP长连接，
     *      1.NAT映射不过期
     *      2.服务端不会断开长时间不使用的链接
     * 如果ping失败，则等待重连策略执行，重新建立链接，再次开启ping线程
     * @param mConnection
     */
    private void addPingManager(final AbstractXMPPConnection mConnection){
        final PingManager pingManager = PingManager.getInstanceFor(mConnection);
        //开启Ping线程，每90S ping一次服务器
        pingManager.setPingInterval(ConstantConfig.DEFAULT_PINGINTERVAL);
        pingManager.registerPingFailedListener(new PingFailedListener() {
            //ping失败则进行重连
            public void pingFailed() {
                new Thread(new Runnable() {
                    public void run() {
                        //如果没有连接上则休眠5s，再次检查
                        while (!checkConnection()){
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //如果连接上，则再次开启心跳发送线程
                        pingManager.setPingInterval(ConstantConfig.DEFAULT_PINGINTERVAL);
                    }
                }).start();
            }
        });

    }
}
