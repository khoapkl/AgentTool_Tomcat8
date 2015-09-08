package com.dell.enterprise.agenttool.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CacheControlFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Last-Modified", new Date().toString());
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
        System.out.println("cache thiendk");
        
        
        
//        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
//        resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        resp.setDateHeader("Expires", 0);
        
        chain.doFilter(request, response);
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}