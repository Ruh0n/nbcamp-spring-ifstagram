package com.nbcampif.ifstagram.domain.auth;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcampif.ifstagram.domain.auth.service.AuthService;
import com.nbcampif.ifstagram.global.common.TestValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AuthIntegrationTest implements TestValues {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private AuthService authService;


  @Nested
  @DisplayName(value = "회원가입")
  class signup {

    @Order(1)
    @Test
    void success() throws Exception {
      // given

      // when
      mockMvc.perform(post("/api/v1/auth/signup")
              .contentType(APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(SIGNUP_REQUEST_DTO1)))

          // then
          .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    @DisplayName(value = "fail: already existing user")
    void fail1() throws Exception {
      // given

      // when
      mockMvc.perform(post("/api/v1/auth/signup")
              .contentType(APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(SIGNUP_REQUEST_DTO1)))

          // then
          .andExpect(status().isBadRequest());
    }

    @Order(3)
    @Test
    @DisplayName(value = "fail: validation fail")
    void fail2() throws Exception {
      // given

      // when
      mockMvc.perform(post("/api/v1/auth/signup")
              .contentType(APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(SIGNUP_REQUEST_DTO_VALIDATION_FAIL)))

          // then
          .andExpect(status().isBadRequest());
    }

    @Order(3)
    @Test
    @DisplayName(value = "fail: different confirm password")
    void fail3() throws Exception {
      // given

      // when
      mockMvc.perform(post("/api/v1/auth/signup")
              .contentType(APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(SIGNUP_REQUEST_DTO_CONFIRM_PASSWORD_FAIL)))

          // then
          .andExpect(status().isBadRequest());
    }

  }

  @Nested
  @DisplayName(value = "로그인")
  class
  login {

    @BeforeEach
    void setUp() {
      try {
        authService.signup(SIGNUP_REQUEST_DTO2);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    @Test
    void success() throws Exception {
      // when
      mockMvc.perform(post("/api/v1/auth/login")
              .contentType(APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(LOGIN_REQUEST_DTO2)))
          .andDo(print())

          // then
          .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "fail: wrong password")
    void fail1() throws Exception {
      // given

      // when
      mockMvc.perform(post("/api/v1/auth/login")
              .contentType(APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(LOGIN_REQUEST_DTO_WRONG_PASSWORD)))
          .andDo(print())

          // then
          .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName(value = "fail: not existing user")
    void fail2() throws Exception {
      // given

      // when
      mockMvc.perform(post("/api/v1/auth/login")
              .contentType(APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(LOGIN_REQUEST_DTO_NOT_EXISTING_USER)))
          .andDo(print())

          // then
          .andExpect(status().is5xxServerError());
    }

  }

}
