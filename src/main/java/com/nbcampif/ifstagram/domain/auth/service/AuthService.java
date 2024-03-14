package com.nbcampif.ifstagram.domain.auth.service;

import com.nbcampif.ifstagram.domain.admin.dto.LoginRequestDto;
import com.nbcampif.ifstagram.domain.auth.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

  void signup(SignupRequestDto requestDto);

  void login(LoginRequestDto requestDto, HttpServletResponse response);

}
