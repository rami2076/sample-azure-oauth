package com.example;

import com.azure.spring.aad.webapp.AADOAuth2AuthorizationRequestResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile("custom")
public class Config extends WebSecurityConfigurerAdapter {
  @Bean
  public FilterRegistrationBean fugaFilter() {
    FilterRegistrationBean bean = new FilterRegistrationBean(new FugaFilter());
    bean.addUrlPatterns("/*","/");
    // HogeFilterの後に実行される
    bean.setOrder(Integer.MIN_VALUE + 1);
    return bean;
  }
  private final CustomOAuth2UserService customOAuth2UserService;
 // private final CustomOAuth2TokenService customOAuth2TokenService;


private final AADOAuth2AuthorizationRequestResolver aadoAuth2AuthorizationRequestResolver;


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http.addFilterBefore(new FugaFilter(), SecurityContextPersistenceFilter.class)
        //.addFilterAt(fugaFilter,Filter.class)
        .authorizeRequests(a -> a
            .antMatchers("/", "/error", "/webjars/**").permitAll()
            .anyRequest().authenticated()
        )
        .exceptionHandling(e -> e
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        )

        .oauth2Login()
        .authorizationEndpoint()
          .authorizationRequestResolver(aadoAuth2AuthorizationRequestResolver)
          .and()

        //.defaultSuccessUrl("/cb-login")
        .tokenEndpoint()
       // .accessTokenResponseClient(customOAuth2TokenService)
          .and()
        .userInfoEndpoint()
        .userService(customOAuth2UserService);
    // @formatter:on
  }



//
//    @Bean // 認証プロバイダのBean定義
//   public ClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(
//
//                ClientRegistration.withRegistrationId("azure")
//                        .clientId("ccc")
//                        .clientSecret("cccc")
//                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                        .redirectUri("http://localhost:8080/cb-login")
//                        .scope("openid")
//                        .clientName("Azure Active Directory")
//                        .authorizationUri("https://login.microsoftonline.com/xxx/oauth2/authorize")
//                        .tokenUri("https://login.microsoftonline.com/xxx/oauth2/token")
//                        //.jwkSetUri("https://auth.login.yahoo.co.jp/yconnect/v2/jwks")
//                        .userInfoUri("https://graph.windows.net/me?api-version=1.6")
//                        .build()
//        );
//    }
}
