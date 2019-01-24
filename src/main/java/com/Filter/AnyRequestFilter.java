package com.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
@WebFilter(
        filterName = "AnyRequest",
        urlPatterns = {"/*"},
        dispatcherTypes = { DispatcherType.REQUEST,DispatcherType.FORWARD,DispatcherType.ASYNC}
)
public class AnyRequestFilter implements Filter {
    private String name;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.name=filterConfig.getFilterName();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Entering " + this.name + ".doFilter().");
        filterChain.doFilter(
                new HttpServletRequestWrapper((HttpServletRequest)servletRequest),
                new HttpServletResponseWrapper((HttpServletResponse)servletResponse)
        );
        if(servletRequest.isAsyncSupported() && servletRequest.isAsyncStarted())
        {
            AsyncContext context = servletRequest.getAsyncContext();
            System.out.println("Leaving " + this.name + ".doFilter(), async " +
                    "context holds wrapped request/response = " +
                    !context.hasOriginalRequestAndResponse());
        }
        else
            System.out.println("Leaving " + this.name + ".doFilter().");
    }

    @Override
    public void destroy() {

    }
}
