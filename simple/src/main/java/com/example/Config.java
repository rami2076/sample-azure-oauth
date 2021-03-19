//package com.example;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.HttpStatusEntryPoint;
//import org.springframework.security.web.context.SecurityContextPersistenceFilter;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class Config extends WebSecurityConfigurerAdapter {
//  @Bean
//  public FilterRegistrationBean fugaFilter() {
//    FilterRegistrationBean bean = new FilterRegistrationBean(new FugaFilter());
//    bean.addUrlPatterns("/*","/");
//    // HogeFilterの後に実行される
//    bean.setOrder(Integer.MIN_VALUE + 1);
//    return bean;
//  }
//  private final CustomOAuth2UserService customOAuth2UserService;
// // private final CustomOAuth2TokenService customOAuth2TokenService;
//
//
//
//
//
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    // @formatter:off
//    http.addFilterBefore(new FugaFilter(), SecurityContextPersistenceFilter.class)
//        //.addFilterAt(fugaFilter,Filter.class)
//        .authorizeRequests(a -> a
//            .antMatchers("/", "/error", "/webjars/**").permitAll()
//            .anyRequest().authenticated()
//        )
//        .exceptionHandling(e -> e
//            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//        )
//
//        .oauth2Login()
//
//        .tokenEndpoint()
//       // .accessTokenResponseClient(customOAuth2TokenService)
//        .and()
//        .userInfoEndpoint()
//        .userService(customOAuth2UserService);
//    // @formatter:on
//  }
//}
