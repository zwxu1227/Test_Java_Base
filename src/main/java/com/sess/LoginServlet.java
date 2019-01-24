package com.sess;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

@WebServlet(
        name = "loginServlet",
        urlPatterns = "/login"
)
public class LoginServlet extends HttpServlet {
    private  static  final Map<String,String> userDataBase=new Hashtable<>();
    static {
        userDataBase.put("Nicholas", "password");
        userDataBase.put("Sarah", "drowssap");
        userDataBase.put("Mike", "wordpass");
        userDataBase.put("John", "green");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String language = req.getParameter("language");
        if("CN".equalsIgnoreCase(language))
            Config.set(req, Config.FMT_LOCALE, Locale.SIMPLIFIED_CHINESE);
        HttpSession session = req.getSession();
        if(req.getParameter("logout") != null)
        {
            session.invalidate();
            resp.sendRedirect("login");
            return;
        }
        else if(session.getAttribute("username") != null)
        {
            resp.sendRedirect("tickets");
            return;
        }

        req.setAttribute("loginFailed", false);
        req.getRequestDispatcher("/WEB-INF/login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session.getAttribute("username") != null)
        {
            resp.sendRedirect("tickets");
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(username == null || password == null ||
                !LoginServlet.userDataBase.containsKey(username) ||
                !password.equals(LoginServlet.userDataBase.get(username)))
        {
            req.setAttribute("loginFailed", true);
            req.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp")
                    .forward(req, resp);
        }
        else
        {
            session.setAttribute("username", username);
            req.changeSessionId();
            resp.sendRedirect("tickets");
        }
    }
}
