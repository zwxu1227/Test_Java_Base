package com.Filter;

import org.apache.logging.log4j.ThreadContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@WebFilter(filterName = "LoggingFilter",
        urlPatterns = "/*",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR, DispatcherType.ASYNC}
)
public class LoggingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        boolean clear = false;
        if(!ThreadContext.containsKey("id"))
        {
            clear = true;
            ThreadContext.put("id", UUID.randomUUID().toString());
            HttpSession session = ((HttpServletRequest)req).getSession(false);
            if(session != null)
                ThreadContext.put("username",
                        (String)session.getAttribute("username"));
        }

        try
        {
            chain.doFilter(req, resp);
        }
        finally
        {
            if(clear)
                ThreadContext.clearAll();
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
