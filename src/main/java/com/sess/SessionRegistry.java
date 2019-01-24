package com.sess;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public final class SessionRegistry {
    private static  final Map<String,HttpSession> SESSION=new Hashtable<>();
    public static void addSession(HttpSession session){
        SESSION.put(session.getId(),session);
    }

    public  static  void updateSessionId(HttpSession session,String oldSessionId)
    {
        synchronized (SESSION)
        {
            SESSION.remove(oldSessionId);
            addSession(session);
        }
    }

    public static  void removeSession(HttpSession session){
        SESSION.remove(session.getId());
    }

    public static List<HttpSession> getAllSessions(){
        return  new ArrayList <>(SESSION.values());
    }

    public  static  int getNumberOfSessions(){
        return SESSION.size();
    }
}
