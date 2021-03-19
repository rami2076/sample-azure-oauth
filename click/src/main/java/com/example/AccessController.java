package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.security.Principal;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccessController {

  private static final ObjectMapper MAPPER =
      new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

  //  @GetMapping("/cb-login/{code}/{state}/{session_state}")
  //  public String login(@RequestParam("code") String code,
  //      @RequestParam("state") String state,
  //      @RequestParam("session_state") String session_state,
  //      HttpServletRequest req, HttpServletResponse res) {
  //
  //    return "redirect:/a";
  //  }

  //  @GetMapping("/cb-login")
  //  public String login(@AuthenticationPrincipal OAuth2User principal) {
  //
  //    return "redirect:/index";
  //  }

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/login/oauth2/code/{code}/{state}/{session_state}")
  public String logina(
      @PathVariable String code,
      @PathVariable String state,
      @PathVariable String session_state,
      @RequestBody String string) {

    System.out.println(string);
    return "redirect:/index";
  }

  @PreAuthorize("hasRole('SSO tt_HenseihyoSystem Users')")
  @SneakyThrows
  @GetMapping("/graph")
  @ResponseBody
  public String graph(
      @RegisteredOAuth2AuthorizedClient("graph") OAuth2AuthorizedClient graphClient,
      @AuthenticationPrincipal OidcUser b) {

    // toJsonString() is just a demo.
    // graphClient contains access_token. We can use this access_token to access resource server.
    // return toJsonString(graphClient);

    b.getAuthorities().forEach(a -> System.out.println(a.getAuthority()));

    return MAPPER.writeValueAsString(graphClient);
  }
  //  @GetMapping("/cb-login")
  //  public String login(@AuthenticationPrincipal OAuth2User principal) {
  //
  //    return "redirect:/index";
  //  }

}
