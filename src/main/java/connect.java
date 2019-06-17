//import org.jivesoftware.smack.AbstractXMPPConnection;
//import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
//import org.jivesoftware.smack.chat.Chat;
//import org.jivesoftware.smack.chat.ChatManager;
//import org.jivesoftware.smack.chat.ChatManagerListener;
//import org.jivesoftware.smack.chat.ChatMessageListener;
//import org.jivesoftware.smack.packet.Message;
//import org.jivesoftware.smack.tcp.XMPPTCPConnection;
//import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
//import org.jivesoftware.smack.util.StringUtils;
//
//import java.util.Iterator;
//
///**
//  * Created by huang(jy) on 2018/3/20.
//  */
//public class connect {
//    public static void main(String[] args) {
//        try {
//
//            String serviceName = "10.128.3.145";//openfire服务器名称
//            String host = "10.128.3.145";//openfire服务器地址
//            int port = 5222; //客户端到服务器,默认5222
//            String myUsername = "test1";//帐号
//            String myPassword = "test";//密码
//            String friendUsername = "_test2";//要发消息的对象名称
//            XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
//            builder.setResource("Smack");//终端名称，如Smack，Spark等，JID的一部分，可以省略
//            builder.setUsernameAndPassword(myUsername, myPassword);
//            builder.setServiceName(serviceName);
//            builder.setHost(host);
//            builder.setPort(port);
//            builder.setSecurityMode(SecurityMode.disabled);
//            builder.setDebuggerEnabled(false);
//            XMPPTCPConnectionConfiguration config = builder.build();
//            AbstractXMPPConnection c = new XMPPTCPConnection(config);
//            c.connect();
//            c.login();
//
//
//
//            System.out.println("Authenticated = " + c.isAuthenticated());
//            ChatManager chatmanager = ChatManager.getInstanceFor(c);
//            //创建本次绘画，指定对方JID，用户名@服务名/资源名（可省）
//            Chat newChat = chatmanager.createChat(friendUsername + "@" + serviceName);
//
//            //监听接收对方发回来的消息，可以与创建聊天鞋在一起，也可以分开如下
////            Chat newChat = chatmanager.createChat(friendUsername + "@" + serviceName, new ChatMessageListener() {
////                public void processMessage(Chat chat, Message message) {
////
////                }
////            })
//            //监听对方发来的消息
//            final ChatMessageListener messageListener = new ChatMessageListener() {
//                public void processMessage(Chat arg0, Message message) {
//                    String messageBody = message.getBody();
//                    if (StringUtils.isNotEmpty(messageBody)) {
//                        try {
//                            arg0.sendMessage("你刚说的是:" + messageBody);
//                        } catch (Exception e) {
//
//                        }
//                    }
//                }
//            };
//            ChatManagerListener chatManagerListener = new ChatManagerListener() {
//                public void chatCreated(Chat chat, boolean arg1) {
//                    chat.addMessageListener(messageListener);
//                }
//            };
//            chatmanager.addChatListener(chatManagerListener);
//            try {
//                newChat.sendMessage("我来咯");
//            } catch (Exception e) {
//                System.out.println("Error Delivering block");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
