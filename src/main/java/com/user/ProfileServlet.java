package com.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Hashtable;

@WebServlet(
        name="profileServlet",
        urlPatterns = "/profile"
)
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user=new User();
        user.setUserId(19384L);
        user.setUsername("Coder314");
        user.setFirstName("John");
        user.setLastName("Smith");
        Hashtable<String,Boolean> permissions=new Hashtable <>();
        permissions.put("user", true);
        permissions.put("moderator", true);
        permissions.put("admin", false);
        user.setPermissions(permissions);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/Hello_EL/profile.jsp")
                .forward(req, resp);
        //super.doGet(req, resp);
    }
}
