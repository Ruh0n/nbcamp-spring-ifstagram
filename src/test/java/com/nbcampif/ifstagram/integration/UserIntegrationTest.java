package com.nbcampif.ifstagram.integration;

import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcampif.ifstagram.domain.user.UserRole;
import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.domain.user.repository.UserRepository;
import com.nbcampif.ifstagram.global.common.TestValues;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserIntegrationTest implements TestValues {

  @Autowired
  private MockMvc mockMvc;

  private final Authentication testAuthentication = new TestingAuthenticationToken(TEST_USER1, null, UserRole.USER.name());

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    try {
      userRepository.createUser(TEST_USER1);
      userRepository.createUser(TEST_USER2);
    } catch (DataIntegrityViolationException e) {
      System.out.println(e.getMessage());
    }
  }

  @Nested
  @DisplayName("내 정보 조회")
  class myPage {

    @Test
    void success() throws Exception {
      // given
      User user = TEST_USER1;

      // when
      mockMvc.perform(get("/api/v1/users/my-page").with(authentication(testAuthentication)))

          // then
          .andExpect(jsonPath("$.message").value(endsWith("성공")))
          .andExpect(jsonPath("$.data.email").value(user.getEmail()))
          .andExpect(jsonPath("$.data.nickname").value(user.getNickname()))
          .andExpect(jsonPath("$.data.profileImage").value(user.getProfileImage()))
          .andExpect(jsonPath("$.data.reportedCount").value(user.getReportedCount()));
    }

  }

  @Nested
  @DisplayName("팔로우")
  class follow {

    @Test
    void success() throws Exception {
      // given
      User toUser = TEST_USER2;

      URI apiUri = new URI("/api/v1/users/" + toUser.getUserId() + "/follow");

      // when
      mockMvc.perform(post(apiUri).with(authentication(testAuthentication)))

          // then
          .andExpect(jsonPath("$.message").value(endsWith("성공")));
    }

  }

  @Nested
  @DisplayName("내 정보 수정")
  class updateUser {

    @Test
    void success() throws Exception {
      // given

      // when
      mockMvc.perform(put("/api/v1/users/my-page").with(authentication(testAuthentication))
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(TEST_USER_UPDATE_REQUEST_DTO)))

          // then
          .andExpect(jsonPath("$.message").value(endsWith("성공")))
          .andExpect(jsonPath("$.data.introduction").value(TEST_USER_UPDATE_REQUEST_DTO.getIntroduction()))
          .andExpect(jsonPath("$.data.nickname").value(TEST_USER_UPDATE_REQUEST_DTO.getNickname()))
          .andExpect(jsonPath("$.data.profileImage").value(TEST_USER_UPDATE_REQUEST_DTO.getProfileImage()));
    }

  }

  @Nested
  @DisplayName("회원 신고")
  class reportUser {

    @Test
    void success() throws Exception {
      // given
      Long reportedUserId = TEST_USER2.getUserId();
      URI apiUri = new URI("/api/v1/users/reports/" + reportedUserId);

      // when
      mockMvc.perform(post(apiUri).with(authentication(testAuthentication)))

          // then
          .andExpect(jsonPath("$.message").value(endsWith("신고되었습니다")));
    }

  }

}
