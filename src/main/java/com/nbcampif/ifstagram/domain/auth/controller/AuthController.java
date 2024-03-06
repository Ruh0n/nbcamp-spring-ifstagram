package com.nbcampif.ifstagram.domain.auth.controller;

import com.nbcampif.ifstagram.domain.admin.dto.LoginRequestDto;
import com.nbcampif.ifstagram.domain.auth.dto.SignupRequestDto;
import com.nbcampif.ifstagram.domain.auth.service.AuthService;
import com.nbcampif.ifstagram.global.dto.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<CommonResponse<Void>> signup(
      @Validated @RequestBody SignupRequestDto requestDto
  ) {
    authService.signup(requestDto);
    return CommonResponse.ok("회원가입 성공", null);
  }

  @PostMapping("/login")
  public ResponseEntity<CommonResponse<Void>> login(
      @Validated @RequestBody LoginRequestDto requestDto, HttpServletResponse response
  ) {
    authService.login(requestDto, response);
    return CommonResponse.ok("로그인 성공", null);
  }

}
