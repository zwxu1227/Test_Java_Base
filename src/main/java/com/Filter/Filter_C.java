package com.Filter;

import javax.servlet.*;
import java.io.IOException;

public class Filter_C implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Entering FilterC.doFilter().");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("Leaving FilterC.doFilter().");
    }

    @Override
    public void destroy() {

    }
}
