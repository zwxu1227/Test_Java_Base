package com.Filter;

import javax.servlet.*;
import java.io.IOException;

public class Filter_A implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Entering FilterA.doFilter().");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("Leaving FilterA.doFilter().");
    }

    @Override
    public void destroy() {

    }
}
