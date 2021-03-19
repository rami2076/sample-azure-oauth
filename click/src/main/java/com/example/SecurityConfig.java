package com.example;

import com.azure.spring.aad.webapp.AADHandleConditionalAccessFilter;
import com.azure.spring.aad.webapp.AADWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@EnableWebSecurity
@Profile("default")
public class SecurityConfig extends AADWebSecurityConfigurerAdapter {
  private final OAuth2UserService<OidcUserRequest, OidcUser> customOAuth2UserService;

  private static final String LOGIN_PAGE = "/oauth2/authorization/azure";

  @Autowired
  public SecurityConfig(
      @Qualifier(value = "customOAuth2OidUserService")
          OAuth2UserService<OidcUserRequest, OidcUser> customOAuth2UserService) {
    this.customOAuth2UserService = customOAuth2UserService;
  }

  /** Add configuration logic as needed. */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // super.configure(http);
    System.out.println("test");
    http.authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        //        .antMatchers("/css/**","/index")
        //        .permitAll()
        //        .and().
        .oauth2Login()
        .loginPage(LOGIN_PAGE)
        .defaultSuccessUrl(
            "/graph",
            false) // https://stackoverflow.com/questions/56943985/spring-security-and-azure-after-restart-app-tries-to-reauthorize-but-azures
        //        .redirectionEndpoint()
        //        .baseUri("/graph")
        //        .and()

        .authorizationEndpoint()
        .authorizationRequestResolver(requestResolver())
        .and()
        .tokenEndpoint()
        .accessTokenResponseClient(accessTokenResponseClient())
        .and()
        .userInfoEndpoint()
        // .userService(customOAuth2UserService)
        .oidcUserService(customOAuth2UserService)

        // .oidcUserService(customOAuth2UserService)
        .and()
        .and()
        .logout()
        .logoutSuccessHandler(oidcLogoutSuccessHandler())
        .and()
        .addFilterBefore(new AADHandleConditionalAccessFilter(), ExceptionTranslationFilter.class);
  }
}
