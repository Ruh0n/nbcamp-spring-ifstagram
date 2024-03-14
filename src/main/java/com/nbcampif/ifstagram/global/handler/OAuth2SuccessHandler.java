package com.nbcampif.ifstagram.global.handler;

import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.global.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j(topic = "OAUTH2_SUCCESS_HANDLER")
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication
  ) {
    User user = (User) authentication.getPrincipal();
    Long userId = user.getUserId();

    String accessToken = jwtTokenProvider.generateAccessToken(userId, user.getRole()
        .getAuthority());
    jwtTokenProvider.generateRefreshToken(userId, user.getRole().getAuthority());

    jwtTokenProvider.addAccessTokenToCookie(accessToken, response);
  }

}
