import communicate.XMPPSession;
import communicate.XMPPSessionFactory;
import communicate.XMPPSessionFactoryBuilder;
import communicate.configuration.ConstantConfig;
import org.junit.Test;

import java.util.Map;

public class test {
//    public static void main(String args[]){
//        XMPPSessionFactory xmppSessionFactory = new XMPPSessionFactoryBuilder().build();
//        XMPPSession xmppSession = xmppSessionFactory.openSession();
//        xmppSession.login("test1","test");
//        xmppSession.getSingleChat("test2");
//        xmppSession.sendMessage("test2","hello");
//        xmppSession.sendMessage("test2","hello");
//
////        xmppSession.register("test3","test");
//        while (true);
//    }
    @Test
    public void testUser1(){
        XMPPSessionFactory xmppSessionFactory = new XMPPSessionFactoryBuilder().build();
        XMPPSession xmppSession = xmppSessionFactory.openSession();
        //设置文件存储路径
        ConstantConfig.FILE_PATH = "/";
        xmppSession.login("test1","test");
        xmppSession.getSingleChat("test2");
        xmppSession.sendMessage("test2","hello");
        xmppSession.sendMessage("test2","hello");
        xmppSession.sendMessage("test2","hello");
//        xmppSession.register("test3","test");
        while (true);
    }
    @Test
    public void testUser2(){
        XMPPSessionFactory xmppSessionFactory = new XMPPSessionFactoryBuilder().build();
        XMPPSession xmppSession = xmppSessionFactory.openSession();
        xmppSession.login("test2","test");
//        xmppSession.getSingleChat("test1");
//        xmppSession.sendMessage("test1","hello");
//        xmppSession.sendMessage("test1","hello");
//        xmppSession.sendFile("test1","E:\\testFileTran.txt");
//        xmppSession.sendMessage("test1","hello");
//        Map offlinemsg = xmppSession.getOfflineMessage();
//        System.out.println(offlinemsg);
        xmppSession.addFriend("test3","test3nickname");
        Map<String, String> friends = xmppSession.getAllFriends();
        System.out.println(friends);
//        xmppSession.register("test3","test");

        while (true);
    }

}
