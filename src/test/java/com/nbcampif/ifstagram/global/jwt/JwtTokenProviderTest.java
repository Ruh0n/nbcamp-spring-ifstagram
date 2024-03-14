package com.nbcampif.ifstagram.global.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.domain.user.repository.UserRepository;
import com.nbcampif.ifstagram.global.common.TestValues;
import com.nbcampif.ifstagram.global.jwt.repository.RefreshTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest implements TestValues {

  @Mock
  RefreshTokenRepository refreshTokenRepository;
  @Mock
  UserRepository userRepository;

  @InjectMocks
  private JwtTokenProvider jwtTokenProvider;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", "IAMASUPERSECRETKEYIAMASUPERSECRETKEYIAMASUPERSECRETKEY");
    jwtTokenProvider.init();
  }

  @Nested
  @DisplayName(value = "access 토큰 생성")
  class generateAccessToken {

    @Test
    void success() {
      // given
      User user = TEST_USER1;
      Long userId = user.getUserId();
      String role = user.getRole().name();

      // when
      String accessToken = jwtTokenProvider.generateAccessToken(userId, role);

      // then
      assertThat(accessToken).startsWith("Bearer ");
    }

  }

  @Nested
  @DisplayName(value = "refresh 토큰 생성")
  class generateRefreshToken {

    @Test
    void success() {
      // given
      User user = TEST_USER1;
      Long userId = user.getUserId();
      String role = user.getRole().name();

      // when
      String accessToken = jwtTokenProvider.generateRefreshToken(userId, role);

      // then
      assertThat(accessToken).startsWith("Bearer ");
    }

  }

  @Nested
  @DisplayName(value = "토큰 상태 확인")
  class checkTokenState {

    @Test
    void valid() {
      // given
      String tokenValue = jwtTokenProvider.generateAccessToken(TEST_USER1.getUserId(), TEST_USER1.getRole()
          .name());
      String token = jwtTokenProvider.substringToken(tokenValue);

      // when
      TokenState result = jwtTokenProvider.checkTokenState(token);

      // then
      assertEquals(TokenState.VALID, result);
    }

  }

  @Nested
  @DisplayName(value = "`Bearer ` 잘라내기")
  class substringToken {

    @Test
    void success() {
      // given
      String tokenValue = jwtTokenProvider.generateAccessToken(TEST_USER1.getUserId(), TEST_USER1.getRole()
          .name());

      // when
      String token = jwtTokenProvider.substringToken(tokenValue);

      // then
      assertThat(token).doesNotContain("Bearer ");
      assertEquals(tokenValue.length(), token.length() + 7);
    }

  }

  @Nested
  @DisplayName(value = "토큰에서 유저 정보 가져오기")
  class getUserFromToken {

    @Test
    void success() {
      // given
      String tokenValue = jwtTokenProvider.generateAccessToken(TEST_USER1.getUserId(), TEST_USER1.getRole()
          .name());
      String token = jwtTokenProvider.substringToken(tokenValue);

      given(userRepository.findUserOrElseThrow(TEST_USER1.getUserId())).willReturn(TEST_USER1);

      // when
      User user = jwtTokenProvider.getUserFromToken(token);

      // then
      assertEquals(TEST_USER1.getUserId(), user.getUserId());
    }

  }

}
