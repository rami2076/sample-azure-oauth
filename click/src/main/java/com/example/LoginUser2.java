package com.example;

import java.util.Collection;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

/** {@link DefaultOidcUser} */
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUser2 implements OidcUser {

  /** 姓名の区切り文字 */
  private static String NAME_DELIMITER = " ";

  /** 社員番号 */
  @Getter @Setter private String employeeNo;
  /** 姓 */
  @Getter @Setter private String lastName;
  /** 名 */
  @Getter @Setter private String firstName;
  /** 会社名称 */
  @Getter @Setter private String companyName;
  /** 部課名称 */
  @Getter @Setter private String bukaName;
  /** セキュリティロール名称 */
  @Getter @Setter private String roleName;
  /** セキュリティロールId */
  @Getter @Setter private Integer roleId;

  @Setter private DefaultOidcUser defaultOidcUser;

  @Override
  public Map<String, Object> getClaims() {
    return defaultOidcUser.getClaims();
  }

  @Override
  public OidcUserInfo getUserInfo() {
    return defaultOidcUser.getUserInfo();
  }

  @Override
  public OidcIdToken getIdToken() {
    return defaultOidcUser.getIdToken();
  }

  @Override
  public Map<String, Object> getAttributes() {
    return defaultOidcUser.getAttributes();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return defaultOidcUser.getAuthorities();
  }

  @Override
  public String getName() {
    return lastName + NAME_DELIMITER + firstName;
  }
}
