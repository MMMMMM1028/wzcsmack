package communicate;

import communicate.XMPPConnectionManager.ConnectionManager;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.roster.RosterEntry;

import java.util.List;
import java.util.Map;

/**
 * facade 接口，所有方法都直接通过XMPPSession调用
 * todo 所有需要返回的（登陆，发送消息结果，注册等）都需要通过handler通知
 * todo 创建线程，在线程里发送handler
 */
public class XMPPSessionImpl implements XMPPSession{
    private ConnectionManager connectionManager;
    private XMPPChatManager xmppChatManager;
    private XMPPAccountManager xmppAccountManager;
    public XMPPSessionImpl(ConnectionManager connectionManager, XMPPChatManager xmppChatManager, XMPPAccountManager xmppAccountManager){
        this.connectionManager = connectionManager;
        this.xmppChatManager = xmppChatManager;
        this.xmppAccountManager = xmppAccountManager;
    }

    public boolean login(String uname, String pwd) {
        return xmppAccountManager.login(uname,pwd);
    }

    public boolean register(String uname, String pwd) {
        return xmppAccountManager.register(uname,pwd);
    }

    public Map<String, String> getAllFriends(){
        return xmppAccountManager.getAllFriends();
    }
    public boolean addFriend(String uname, String nickname){
        return xmppAccountManager.addFriend(uname, nickname);
    }
    public boolean removeFriend(String uname){
        return xmppAccountManager.removeFriend(uname);
    }


    public Map<String, List<String>> getOfflineMessage(){
        return xmppChatManager.getOfflineMessage();
    }
    public Chat getSingleChat(String uname){
        return xmppChatManager.getSingleChat(uname);
    }
    public void sendMessage(String uname, String message){
        xmppChatManager.sendMessage(uname,message);
    }

    public void sendFile(String uname, String filePath) {
        xmppChatManager.sendFile(uname,filePath);
    }

    public boolean createChatRoom(String roomName, String nickname) {
        return xmppChatManager.createChatRoom(roomName,nickname);
    }

    public boolean sendGroupMessage(String rootName, String message) {
        return xmppChatManager.sendChatGroupMessage(rootName, message);
    }
}
