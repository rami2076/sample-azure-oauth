package com.example;

import java.security.AuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
      throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
    System.out.println(oAuth2User);
    return oAuth2User;
    //    try {
//      return processOAuth2User(oAuth2UserRequest, oAuth2User);
//    } catch (AuthenticationException ex) {
//      throw ex;
//    } catch (Exception ex) {
//      // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
//      throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
//    }
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
    return new CustomUserPrincipal(oAuth2User);
  }


}