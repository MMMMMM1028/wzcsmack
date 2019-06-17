package communicate;

import communicate.XMPPConnectionManager.ConnectionManager;
import communicate.configuration.ConstantConfig;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smackx.iqregister.AccountManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 用户管理，主要方法
 * 注册
 * 登陆
 * 获取好友列表
 * 好友管理
 * 上传头像
 * 获取加入群组
 * 添加好友
 * 删除好友
 *
 */
public class XMPPAccountManager {
    private static XMPPAccountManager XMPPAccountManager;
    private ConnectionManager mXmppConnectionManager;
//    private AbstractXMPPConnection mConnection;


    private XMPPAccountManager(ConnectionManager mXmppConnectionManager){
        this.mXmppConnectionManager = mXmppConnectionManager;
//        mConnection = this.mXmppConnectionManager.getConnection();
        //设置默认的好友请求接收方式 todo 暂时为全部统一简单
        Roster.setDefaultSubscriptionMode(ConstantConfig.DEFAULT_SUBSCRIPTION_MODE);
    }

    public static XMPPAccountManager getInstance(ConnectionManager mXmppConnectionManager){
        if (XMPPAccountManager == null){
            XMPPAccountManager = new XMPPAccountManager(mXmppConnectionManager);
        }
        return XMPPAccountManager;
    }

    /**
     * 登陆
     * @param account
     * @param pwd
     * @return
     */
    public boolean login(String account, String pwd){
        //todo 网络传输放在子线程，
//        new Thread(new Runnable() {
//            public void run() {
//
//            }
//        }) .start();
        try {
            if (mXmppConnectionManager.getConnection() == null){
                return false;
            }
            mXmppConnectionManager.getConnection().login(account,pwd);
//            // 更改在线状态
//            setPresence(0);
//
//            // 添加连接监听
//            connectionListener = new XMConnectionListener(account, password);
//            getConnection().addConnectionListener(connectionListener);
            if (mXmppConnectionManager.isAuthenticated()){
                ConstantConfig.uname = account;
                return true;
            }
            else
                return false;

        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 注册账号
     * @param account
     * @param pwd
     * @return
     */
    public boolean register(String account, String pwd){
        //todo 获得注册结果
        if (mXmppConnectionManager.getConnection() == null){
            return false;
        }
        try {
            AccountManager.getInstance(mXmppConnectionManager.getConnection()).createAccount(account,pwd);
            return true;
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @return
     */
//    public boolean resetPwd()

    /**
     * 返回好友列表
     * @return JID nickname
     */
    public Map<String ,String > getAllFriends(){
        if (mXmppConnectionManager.getConnection() == null){
            return null;
        }
        Roster roster = Roster.getInstanceFor(mXmppConnectionManager.getConnection());
//        List<Map<String ,String >> entryList = new ArrayList<Map<String ,String >>();
        Map<String ,String> friends = new HashMap<String ,String>();
        Collection<RosterEntry> rosterEntrySet = roster.getEntries();
        Iterator it = rosterEntrySet.iterator();
        while (it.hasNext()){
            RosterEntry rosterEntry = (RosterEntry) it.next();
            friends.put(rosterEntry.getUser(),rosterEntry.getName());
        }
        return friends;
    }

    /**
     * 添加好友，设置昵称，没有分组
     * @param uname
     * @param nickname
     * @return
     */
    public boolean addFriend(String uname, String nickname){
        if(mXmppConnectionManager.getConnection() == null){
            return false;
        }
        String JID = util.getJID(uname);
        try {
            //添加好友，设置昵称，没有分组全在默认分组
            Roster.getInstanceFor(mXmppConnectionManager.getConnection()).createEntry(JID,nickname,null);
            return true;
        } catch (SmackException.NotLoggedInException e) {
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeFriend(String uname){
        if (mXmppConnectionManager.getConnection() == null){
            return false;
        }
        String JID = util.getJID(uname);
        Roster roster = Roster.getInstanceFor(mXmppConnectionManager.getConnection());
        RosterEntry entry = roster.getEntry(JID);
        try {
            Roster.getInstanceFor(mXmppConnectionManager.getConnection()).removeEntry(entry);
            return true;
        } catch (SmackException.NotLoggedInException e) {
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        return false;
    }

}
