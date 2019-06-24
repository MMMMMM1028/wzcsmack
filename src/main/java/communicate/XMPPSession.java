package communicate;

import org.jivesoftware.smack.chat.Chat;

import java.util.List;
import java.util.Map;

public interface XMPPSession {
    /**
     * 账号管理
     * 登陆
     * 注册
     * 获取好友列表
     * 添加好友
     * 删除好友
     */
    boolean login(String uname, String pwd);
    boolean register(String uname, String pwd);
    void logout();
    /**
     * 返回好友列表
     * @return Map {uname,nickname}
     */
    Map<String,String> getAllFriends();

    /**
     * 添加好友
     * @param uname 对方的账号
     * @param nickname 备注
     * @return
     */
    boolean addFriend(String uname, String nickname);
    boolean removeFriend(String uname);

    /**
     * 聊天管理
     * 获取离线消息
     * 打开单人聊天窗口，必须创建chat即打开聊天窗口才可以发送消息
     * 发送消息
     * 创建群组，todo 实现一部分，没有实现邀请
     * 发送群消息
     */
    Map<String, List<String>> getOfflineMessage();
    Chat getSingleChat(String uname);
    void sendMessage(String uname, String message);
    void sendFile(String uname, String filePath);
    boolean createChatRoom(String roomName, String nickname);
    boolean sendGroupMessage(String rootName, String message);

}
