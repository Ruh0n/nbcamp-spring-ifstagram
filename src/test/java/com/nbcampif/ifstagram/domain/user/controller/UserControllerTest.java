package com.nbcampif.ifstagram.domain.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import com.nbcampif.ifstagram.domain.user.dto.UserUpdateRequestDto;
import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.domain.user.service.UserService;
import com.nbcampif.ifstagram.global.common.TestValues;
import com.nbcampif.ifstagram.global.jwt.JwtTokenProvider;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = {UserController.class})
class UserControllerTest extends TestValues {

  @Autowired
  private WebApplicationContext context;
  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  UserService userService;
  @MockBean
  JwtTokenProvider jwtTokenProvider;

  private MockMvc mvc;
  private FixtureMonkey fixtureMonkey;

  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(springSecurity())
        .defaultRequest(get("/").contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .with(csrf())
            .with(authentication(TEST_AUTHENTICATION)))
        .build();
    fixtureMonkey = FixtureMonkey.builder()
        .plugin(new JakartaValidationPlugin())
        .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
        .build();
  }

  @Nested
  @WithMockUser
  class MyPage {

    @Test
    void success() throws Exception {
      // given
      URI uri = URI.create("/api/v1/users/my-page");

      // when - then
      mvc.perform(get(uri))
          .andDo(print())
          .andExpect(status().isOk());
    }

  }

  @Nested
  class follow {

    @Test
    void success() throws Exception {
      // given
      URI uri = URI.create("/api/v1/users/" + TEST_USER2.getUserId() + "/follow");

      // when - then
      mvc.perform(post(uri))
          .andDo(print())
          .andExpect(status().isOk());
    }

  }

  @Nested
  class updateUser {

    private static final URI url = URI.create("/api/v1/users/my-page");

    @Test
    void success() throws Exception {
      // given
      UserUpdateRequestDto requestDto = fixtureMonkey.giveMeOne(UserUpdateRequestDto.class);

      given(userService.updateUser(any(UserUpdateRequestDto.class), any(User.class)))
          .willReturn(new User("", requestDto.getNickname(), requestDto.getPassword(), requestDto.getProfileImage()));

      // when
      ResultActions resultActions = mvc.perform(put(url).content(objectMapper.writeValueAsString(requestDto)));

      // then
      resultActions.andExpect(status().isOk());
    }

  }

  @Nested
  class reportUser {

    private static final URI url = URI.create("/api/v1/users/reports/2");


    @Test
    void success() throws Exception {
      // when
      ResultActions resultActions = mvc.perform(post(url));

      // then
      resultActions.andExpect(status().isOk());
    }

  }

}
