package com.nbcampif.ifstagram.domain.auth.service;

import com.nbcampif.ifstagram.domain.admin.dto.LoginRequestDto;
import com.nbcampif.ifstagram.domain.auth.dto.SignupRequestDto;
import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.domain.user.repository.RecentPasswordRepository;
import com.nbcampif.ifstagram.domain.user.repository.UserRepository;
import com.nbcampif.ifstagram.domain.user.repository.entity.RecentPassword;
import com.nbcampif.ifstagram.global.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "AUTH_SERVICE")
@RequiredArgsConstructor
@Service
@Transactional
public class AuthServiceImpl extends DefaultOAuth2UserService implements AuthService {

  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;
  private final RecentPasswordRepository recentPasswordRepository;

  @Override
  public void signup(SignupRequestDto requestDto) {
    Optional<User> existingUser = userRepository.findByEmail(requestDto.getEmail());
    if (existingUser.isPresent()) {
      throw new IllegalArgumentException("이미 가입된 이메일입니다.");
    }

    if (!requestDto.isConfirmPasswordCorrect()) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }
    String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

    User newUser = User.ofSignup(requestDto, encodedPassword);
    RecentPassword recentPassword = new RecentPassword(requestDto.getPassword(), newUser.getUserId());

    userRepository.createUser(newUser);
    recentPasswordRepository.save(recentPassword);
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    Map<String, Object> attributes = oAuth2User.getAttributes();
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    String email = (String) kakaoAccount.get("email");

    User user;
    Optional<User> savedUser = userRepository.findByEmail(email);

    if (savedUser.isEmpty()) {
      Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
      String nickname = (String) profile.get("nickname");
      String profileImage = (String) profile.get("profile_image_url");
      user = User.ofOauth2(email, nickname, profileImage);

      userRepository.createUser(user);
    } else {
      user = savedUser.get();
    }

    return user;
  }

  @Override
  public void login(LoginRequestDto requestDto, HttpServletResponse response) {
    String email = requestDto.getEmail();
    User savedUser = userRepository.findByEmailOrElseThrow(email);

    String password = (requestDto.getPassword());
    if (!passwordEncoder.matches(password, savedUser.getPassword())) {
      throw new IllegalArgumentException("잘못된 비밀번호입니다.");
    }

    String accessToken = jwtTokenProvider.generateAccessToken(savedUser.getUserId(), savedUser.getRole()
        .getAuthority());
    jwtTokenProvider.generateRefreshToken(savedUser.getUserId(), savedUser.getRole()
        .getAuthority());

    jwtTokenProvider.addAccessTokenToCookie(accessToken, response);
  }

}
