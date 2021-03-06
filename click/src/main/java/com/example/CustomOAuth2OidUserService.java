package com.example;

import static com.azure.spring.autoconfigure.aad.Constants.DEFAULT_AUTHORITY_SET;
import static com.azure.spring.autoconfigure.aad.Constants.ROLE_PREFIX;

import com.azure.spring.aad.webapp.GraphClient;
import com.azure.spring.autoconfigure.aad.AADAuthenticationProperties;
import com.azure.spring.autoconfigure.aad.AADTokenClaim;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** {@link com.azure.spring.aad.webapp.AADOAuth2UserService} */
@Service("customOAuth2OidUserService")
public class CustomOAuth2OidUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

  private final OidcUserService oidcUserService;
  private final AADAuthenticationProperties properties;
  private final GraphClient graphClient;
  private static final String DEFAULT_OIDC_USER = "loginUser2";

  public CustomOAuth2OidUserService(AADAuthenticationProperties properties) {
    this.properties = properties;
    this.oidcUserService = new OidcUserService();
    this.graphClient = new GraphClient(properties);
  }

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
    // Delegate to the default implementation for loading a user
    OidcUser oidcUser = oidcUserService.loadUser(userRequest);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    ServletRequestAttributes attr =
        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpSession session = attr.getRequest().getSession(true);

    if (authentication != null) {
      return (LoginUser2) session.getAttribute(DEFAULT_OIDC_USER);
    }

    Set<String> groups =
        Optional.of(userRequest)
            .filter(notUsed -> properties.allowedGroupsConfigured())
            .map(OAuth2UserRequest::getAccessToken)
            .map(AbstractOAuth2Token::getTokenValue)
            .map(graphClient::getGroupsFromGraph)
            .orElseGet(Collections::emptySet);
    Set<String> groupRoles =
        groups.stream()
            .filter(properties::isAllowedGroup)
            .map(group -> ROLE_PREFIX + group)
            .collect(Collectors.toSet());
    Set<SimpleGrantedAuthority> authorities =
        groupRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    if (authorities.isEmpty()) {
      authorities = DEFAULT_AUTHORITY_SET;
    }
    String nameAttributeKey =
        Optional.of(userRequest)
            .map(OAuth2UserRequest::getClientRegistration)
            .map(ClientRegistration::getProviderDetails)
            .map(ClientRegistration.ProviderDetails::getUserInfoEndpoint)
            .map(ClientRegistration.ProviderDetails.UserInfoEndpoint::getUserNameAttributeName)
            .filter(StringUtils::hasText)
            .orElse(AADTokenClaim.NAME);
    // Create a copy of oidcUser but use the mappedAuthorities instead
    DefaultOidcUser defaultOidcUser =
        new DefaultOidcUser(authorities, oidcUser.getIdToken(), nameAttributeKey);


    var loginUser2 =
        LoginUser2.builder()
            .employeeNo("a")
            .bukaName("a")
            .companyName("")
            .firstName("tadashi")
            .lastName("hashimoto")
            .roleName("")
            .roleId(1)
            .defaultOidcUser(defaultOidcUser)
            .build();

    session.setAttribute(DEFAULT_OIDC_USER, loginUser2);


    System.out.println("ss");
    return loginUser2;
  }
}
