//import org.jivesoftware.smack.AbstractXMPPConnection;
//import org.jivesoftware.smack.ConnectionConfiguration;
//import org.jivesoftware.smack.SmackException;
//import org.jivesoftware.smack.chat.Chat;
//import org.jivesoftware.smack.chat.ChatManager;
//import org.jivesoftware.smack.chat.ChatManagerListener;
//import org.jivesoftware.smack.chat.ChatMessageListener;
//import org.jivesoftware.smack.packet.Message;
//import org.jivesoftware.smack.tcp.XMPPTCPConnection;
//import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
//
///**
//  * Created by huang(jy) on 2018/3/20.
//  */
//public class _test2 {
//    public static void main(String[] args) throws Exception{
//        try {
//            String serviceName = "10.128.3.145";//openfire服务器名称
//            String host = "10.128.3.145";//openfire服务器地址
//            int port = 5222; //客户端到服务器,默认5222
//            String myUsername = "_test2";//帐号
//            String myPassword = "test";//密码
//            String friendUsername = "test1";//要发消息的对象名称
//            XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
//            builder.setResource("Smack");//终端名称，如Smack，Spark等，JID的一部分，可以省略
//            builder.setUsernameAndPassword(myUsername, myPassword);
//            builder.setServiceName(serviceName);
//            builder.setHost(host);
//            builder.setPort(port);
//            builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
//            builder.setDebuggerEnabled(false);
//            XMPPTCPConnectionConfiguration config = builder.build();
//            AbstractXMPPConnection c = new XMPPTCPConnection(config);
//            c.connect();
//            c.login();
//            System.out.println("Authenticated = " + c.isAuthenticated());
//            ChatManager chatmanager = ChatManager.getInstanceFor(c);
//            chatmanager.addChatListener(new ChatManagerListener() {
//                public void chatCreated(Chat chat, boolean b) {
//                    //自己创建的chat
//                    if (b){
//                        System.out.println("自己创建的chat");
//                    }else {
//                        System.out.println("别人和我创建聊天0");
//                        chat.addMessageListener(new ChatMessageListener() {
//                            public void processMessage(Chat chat, Message message) {
//                                System.out.println(message.getFrom()+"发来消息");
//                                try {
//                                    chat.sendMessage("你刚说的是:"+message.getBody());
//                                } catch (SmackException.NotConnectedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                    }
//                }
//            });
////            ChatManager chatmanager = ChatManager.getInstanceFor(c);
////            //创建本次绘画，指定对方JID，用户名@服务名/资源名（可省）
////            Chat newChat = chatmanager.createChat(friendUsername + "@" + serviceName);
////
////            //监听接收对方发回来的消息，可以与创建聊天鞋在一起，也可以分开如下
//////            Chat newChat = chatmanager.createChat(friendUsername + "@" + serviceName, new ChatMessageListener() {
//////                public void processMessage(Chat chat, Message message) {
//////
//////                }
//////            })
////            //监听对方发来的消息
////            final ChatMessageListener messageListener = new ChatMessageListener() {
////                public void processMessage(Chat arg0, Message message) {
////                    String messageBody = message.getBody();
////                    if (StringUtils.isNotEmpty(messageBody)) {
////                        try {
////                            arg0.sendMessage("你刚说的是:" + messageBody);
////                        } catch (Exception e) {
////
////                        }
////                    }
////                }
////            };
////            ChatManagerListener chatManagerListener = new ChatManagerListener() {
////                public void chatCreated(Chat chat, boolean arg1) {
////                    chat.addMessageListener(messageListener);
////                }
////            };
////            chatmanager.addChatListener(chatManagerListener);
////            try {
////                newChat.sendMessage("我来咯");
////            } catch (Exception e) {
////                System.out.println("Error Delivering block");
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
