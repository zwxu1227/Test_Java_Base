package com.Filter;

import org.apache.commons.lang3.time.StopWatch;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

public class RequestLogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Instant time = Instant.now();
        StopWatch timer = new StopWatch();
        try
        {
            timer.start();
            filterChain.doFilter(servletRequest, servletResponse);
        }
        finally
        {
            timer.stop();
            HttpServletRequest in = (HttpServletRequest)servletRequest;
            HttpServletResponse out = (HttpServletResponse)servletResponse;
            String length = out.getHeader("Content-Length");
            if(length == null || length.length() == 0)
                length = "-";
            System.out.println(in.getRemoteAddr() + " - - [" + time + "]" +
                    " \"" + in.getMethod() + " " + in.getRequestURI() + " " +
                    in.getProtocol() + "\" " + out.getStatus() + " " + length +
                    " " + timer);
        }
    }

    @Override
    public void destroy() {

    }
}
