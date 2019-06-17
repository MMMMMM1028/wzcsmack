package communicate.configuration;

import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.roster.Roster;

/**
 * 定义常量
 * 定义心跳
 * 定义重连策略
 */
public class ConstantConfig {
    //用户名，在登录时保存
    public static String uname = null;
    public static final String SERVICE_NAME = "10.128.3.145";
    public static final String SEVER_HOST = "10.128.3.145";
    public static final int SERVER_PORT = 5222;
    //发送心跳的时间，主要用于保持NAT映射，以及让服务器不断开与客户端的连接，如果服务器收不到客户端发来的消息会主动ping客户端如果ping不到则断开
    public static final int DEFAULT_PINGINTERVAL = 90;
    //随机时间重连策略
    public static final ReconnectionManager.ReconnectionPolicy DEFAULT_RECONNECTION_POLICY =   ReconnectionManager.ReconnectionPolicy.RANDOM_INCREASING_DELAY;
    //是否自动重连
    public static final boolean AUTOMICA_RECONNECT_ENABLED =  true;
    //直接同意所有好友请求，简单
    public static final Roster.SubscriptionMode DEFAULT_SUBSCRIPTION_MODE = Roster.SubscriptionMode.accept_all;

}
