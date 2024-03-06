package com.nbcampif.ifstagram.domain.auth.service;

import com.nbcampif.ifstagram.domain.admin.dto.LoginRequestDto;
import com.nbcampif.ifstagram.domain.auth.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface AuthService {

  void signup(SignupRequestDto requestDto);

  void login(LoginRequestDto requestDto, HttpServletResponse response);

}
