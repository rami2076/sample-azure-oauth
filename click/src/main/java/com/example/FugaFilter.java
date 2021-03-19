package com.example;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FugaFilter implements javax.servlet.Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    //requestsession　にLoginUserがあるか？
    //Redis session 永続化

    //skip
    System.out.println(servletRequest);
    System.out.println(servletResponse);

    System.out.println("aaaa");
    //sessionにある場合
    filterChain.doFilter(servletRequest, servletResponse);

  }
}
