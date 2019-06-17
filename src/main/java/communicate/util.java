package communicate;

import communicate.configuration.ConstantConfig;

public class util {
    /**
     * 将用户名转化为JID
     * @param uname
     * @return
     */
    public static String getJID(String uname){
        String JID = uname + "@" + ConstantConfig.SERVICE_NAME+"/Smack";
        return JID;
    }

    public static String getGID(String roomName){
        String GID = roomName + "@conference." + ConstantConfig.SERVICE_NAME;
        return GID;
    }
}
