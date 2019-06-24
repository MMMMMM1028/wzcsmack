package communicate;

import com.example.dell.jsltevent.ObserverService.Event.FileEvent;
import com.example.dell.jsltevent.ObserverService.Event.InfoEvent;
import communicate.XMPPConnectionManager.ConnectionManager;
import communicate.configuration.ConstantConfig;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.filetransfer.*;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.offline.OfflineMessageManager;
import org.jivesoftware.smackx.xdata.Form;
import com.example.dell.jsltevent.ObserverService.Event.Event;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * todo 接收消息
 * 发送消息
 * todo 发送文件
 * todo 群聊
 * 获取离线消息
 */
public class XMPPChatManager {
    private static XMPPChatManager xmppChatManager;
    private ConnectionManager mXMPPConnectionManager;
    //处理受到的消息 todo 发送给handler
    private ChatMessageListener chatMessageListener;
    // 监听聊天建立，添加消息监听
    private ChatManagerListener chatManagerListener;

    // 当前打开的聊天列表，key为JID，value为对应的chat
    private Map<String,Chat> chatList;

    private XMPPChatManager(ConnectionManager mXmppConnectionManager){
        this.mXMPPConnectionManager = mXmppConnectionManager;
        chatList = new HashMap<String, Chat>();
        addFileTransferListener();
        addMessageListener();

    }

    /**
     * 单例
     * @param mXmppConnectionManager
     * @return
     */
    public synchronized static XMPPChatManager getInstance(ConnectionManager mXmppConnectionManager) {
        if (xmppChatManager == null)
            xmppChatManager = new XMPPChatManager(mXmppConnectionManager);
        return xmppChatManager;
    }


    /**
     * 返回离线消息
     * Map{
     *     "formUser":String
     *     "content": String
     *      todo  JSON {"date","text"}转的String
     * }
     * @return
     */
    public Map<String, List<String>> getOfflineMessage(){
        AbstractXMPPConnection mConnection = mXMPPConnectionManager.getConnection();
        if (mConnection == null)
            return null;
        Map<String, List<String>> offlineMessage = null;
        OfflineMessageManager offlineMessageManager = new OfflineMessageManager(mConnection);
        try {
            List<Message> messageList = offlineMessageManager.getMessages();
            int count = offlineMessageManager.getMessageCount();
            if (count < 0)
                return null;
            offlineMessage  = new HashMap<String, List<String>>();
            for (Message message : messageList){
                String fromUser = message.getFrom();
                String content = message.getBody();
//                //离线消息
//                Map<String, String> history = new HashMap<String, String>();
////                //自己的账号
////                history.put("uname", mConnection.getUser());
//                //发送方的账号
//                history.put("fromUser", fromUser);
//                //文本消息内容
//                history.put("text", message.getBody());
//                history.put("type", "left");
                //如果此条消息的发送者已经添加到offlineMessage则直接添加到value的list中
                if (offlineMessage.containsKey(fromUser)) {
                    offlineMessage.get(fromUser).add(content);
                } else {//第一次受到该发送者的消息，插入到offlineMssage
                    List<String> temp = new ArrayList<String>();
                    temp.add(content);
                    offlineMessage.put(fromUser, temp);
                }
            }
            offlineMessageManager.deleteMessages();
            return offlineMessage;
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 打开聊天窗口，创建聊天
     * @param uname
     * @return
     */
    public Chat getSingleChat(String uname){
        AbstractXMPPConnection mConnection = mXMPPConnectionManager.getConnection();
        if (mConnection == null)
            return null;
        String JID = util.getJID(uname);
        Chat chat = ChatManager.getInstanceFor(mConnection).createChat(JID,this.chatMessageListener);
        //插入到聊天map todo messagegetfrom得到的是JID还是账号,目前当作是JID
        chatList.put(JID,chat);
        // 添加消息监听
        return ChatManager.getInstanceFor(mConnection).createChat(JID,this.chatMessageListener);
//        return ChatManager.getInstanceFor(mConnection).createChat(JID, new ChatMessageListener() {
//            public void processMessage(Chat chat, Message message) {
//                System.out.println(message);
//            }
//        });
    }

    /**
     * 发送消息
     * @param uname
     * @param message
     */
    public void sendMessage(String uname, String message){
        String JID = util.getJID(uname);
        Chat chat = chatList.get(JID);
        try {
            chat.sendMessage(message);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送文件
     * @param uname
     * @param filePath
     */
    public void sendFile(String uname, String filePath){
        AbstractXMPPConnection mConnection = mXMPPConnectionManager.getConnection();
        if (mConnection == null){
            return;
        }
        //文件传输管理器
        FileTransferManager fileTransferManager = FileTransferManager.getInstanceFor(mConnection);
        // 文件传输
        OutgoingFileTransfer outgoingFileTransfer = null;
        outgoingFileTransfer = fileTransferManager.createOutgoingFileTransfer(util.getJID(uname));
        try {
            // 描述不能为空
            outgoingFileTransfer.sendFile(new File(filePath),"");
            long startTime = -1;
            while (!outgoingFileTransfer.isDone()){
                if (outgoingFileTransfer.getStatus().equals(FileTransfer.Status.error)){
                    System.out.println("error!!!"+outgoingFileTransfer.getError());
                }else{
                    double progress = outgoingFileTransfer.getProgress();
                    if(progress>0.0 && startTime==-1){
                        startTime = System.currentTimeMillis();
                    }
                    progress*=100;
                    System.out.println("status="+outgoingFileTransfer.getStatus());
                    System.out.println("progress="+progress+"%");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("used "+((System.currentTimeMillis()-startTime)/1000)+" seconds  ");
            System.out.println(outgoingFileTransfer.getProgress());
        } catch (SmackException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听消息
     */
    private void addMessageListener(){
        //初始化消息监听，以及消息处理
        this.chatMessageListener = new ChatMessageListener() {
            public void processMessage(Chat chat, Message message) {
                String from = message.getFrom();
                String body = message.getBody();
                if (!chatList.containsKey(from)){
                    chatList.put(from,chat);
                }
                Map<String,String> msg = new HashMap<String,String>();
                msg.put("uname",from);
                msg.put("msg",body);
                Event event = InfoEvent.getInstance();
                event.informObserver(msg);
                System.out.println(from+body);
            }
        };

        //监听聊天建立
        this.chatManagerListener = new ChatManagerListener() {
            //对方发启聊天，使用chatMessageListener来处理受到的消息
            public void chatCreated(Chat chat, boolean b) {
                if(!b){
                    chat.addMessageListener(chatMessageListener);
                }
            }
        };
        ChatManager.getInstanceFor(mXMPPConnectionManager.getConnection()).addChatListener(chatManagerListener);
    }

    /**
     * 监听文件
     */
    private void addFileTransferListener(){
        AbstractXMPPConnection mConnection = mXMPPConnectionManager.getConnection();
        FileTransferManager manager = FileTransferManager.getInstanceFor(mConnection);
        manager.addFileTransferListener(new FileTransferListener() {
            public void fileTransferRequest(FileTransferRequest fileTransferRequest) {
                IncomingFileTransfer infile = fileTransferRequest.accept();
                System.out.println("file receiving");
                try {
                    //todo 文件怎么存储,暂时存储在d盘，文件名
                    try {
                        //todo hanlder
                        String from = fileTransferRequest.getRequestor().split("/")[0];
                        String fileName = fileTransferRequest.getFileName();
                        String filePath = ConstantConfig.FILE_PATH+fileName;
                        infile.recieveFile(new File(filePath));
                        Map<String,String> file = new HashMap<String, String>();
                        file.put("from",from);
                        file.put("filepath",filePath);
                        Event event = FileEvent.getInstance();
                        event.informObserver(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (SmackException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建群组
     * @param rootName 群ID
     * @param nickname 群别名
     * @return
     */
    public boolean createChatRoom(String rootName, String nickname){
        AbstractXMPPConnection mConnection = mXMPPConnectionManager.getConnection();
        String GID = util.getGID(rootName);
        MultiUserChat muc = MultiUserChatManager.getInstanceFor(mConnection).getMultiUserChat(GID);
        try {
            //创建聊天室
            muc.create(nickname);
            Form form = muc.getConfigurationForm();
            //要提交的配置表单
            Form submitForm = form.createAnswerForm();
            //群主
            List<String> owners = new ArrayList<String>();
            owners.add(util.getJID(ConstantConfig.uname));
            submitForm.setAnswer("muc#roomconfig_roomowners", owners);
            // 持久的房间
            submitForm.setAnswer("muc#roomconfig_persistentroom", true);
            // 房间仅对成员开放，其他人查不到该房间
            submitForm.setAnswer("muc#roomconfig_membersonly", false);
            // 允许群成员邀请其他人，只能通过邀请进入，因为查不到
            submitForm.setAnswer("muc#roomconfig_allowinvites", true);
            //不许要密码
            submitForm.setAnswer("muc#roomconfig_passwordprotectedroom", false);
            // 能够发现占有者真实 JID 的角色
            submitForm.setAnswer("muc#roomconfig_whois", "anyone");
//            // 开启日志
//            submitForm.setAnswer("muc#roomconfig_enablelogging", true);
//            // 仅允许注册的昵称登录
////            submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
//            // 允许使用者修改昵称
//            submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);
            // 允许用户注册房间
            submitForm.setAnswer("x-muc#roomconfig_registration", false);
            // 发送已完成的表单（有默认值）到服务器来配置聊天室
            muc.sendConfigurationForm(submitForm);
            //增加消息监听，处理群组消息
            muc.addMessageListener(new MessageListener() {
                public void processMessage(Message message) {
                    //todo 处理群组消息
                    System.out.println(message.getFrom());
                }
            });
            return true;
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 发送群聊普通消息
     * @param rootName
     * @param body
     */
    public boolean sendChatGroupMessage(String rootName, String body){
        AbstractXMPPConnection mConnection = mXMPPConnectionManager.getConnection();
        String GID = util.getGID(rootName);
        //群管理对象
        MultiUserChatManager multiUserChatManager = MultiUserChatManager.getInstanceFor(mConnection);
        MultiUserChat multiUserChat = multiUserChatManager.getMultiUserChat(GID);
        //发送信息
        try {
            multiUserChat.sendMessage(body);
            return true;
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void destory(){
        xmppChatManager = null;
    }

}
