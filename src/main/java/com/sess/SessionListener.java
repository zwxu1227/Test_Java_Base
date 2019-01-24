package com.sess;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
@WebListener
public  class SessionListener  implements HttpSessionListener,HttpSessionIdListener {
    private SimpleDateFormat formatter=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
    @Override
    public void sessionIdChanged(HttpSessionEvent e, String s) {
        System.out.println(this.date() + ": Session ID " + s +
                " changed to " + e.getSession().getId());
        SessionRegistry.updateSessionId(e.getSession(), s);
    }

    @Override
    public void sessionCreated(HttpSessionEvent e) {
        System.out.println(this.date() + ": Session " + e.getSession().getId() +
                " created.");
        SessionRegistry.addSession(e.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent e) {
        System.out.println(this.date() + ": Session " + e.getSession().getId() +
                " destroyed.");
        SessionRegistry.removeSession(e.getSession());
    }

    private String date()
    {
        return this.formatter.format(new Date());
    }
}
