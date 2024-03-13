package com.nbcampif.ifstagram.global.common;

import com.nbcampif.ifstagram.domain.admin.dto.LoginRequestDto;
import com.nbcampif.ifstagram.domain.auth.dto.SignupRequestDto;
import com.nbcampif.ifstagram.domain.user.UserRole;
import com.nbcampif.ifstagram.domain.user.dto.UserUpdateRequestDto;
import com.nbcampif.ifstagram.domain.user.model.User;
import org.springframework.security.authentication.TestingAuthenticationToken;

public class TestValues {

  public final static Long TEST_ID1 = 1L;
  public final static String TEST_EMAIL1 = "TestUser1@email.com";
  public final static String TEST_NICKNAME1 = "TestUser1";
  public final static String TEST_PASSWORD1 = "Password123";
  public final static String TEST_PROFILE_IMAGE1 = "https://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/img_640x640.jpg";
  public final static User TEST_USER1 = new User(TEST_ID1, TEST_EMAIL1, TEST_NICKNAME1, TEST_PASSWORD1, TEST_PROFILE_IMAGE1, "", 0L, UserRole.USER);

  public final static Long TEST_ID2 = 2L;
  public final static String TEST_EMAIL2 = "TestUser2@email.com";
  public final static String TEST_NICKNAME2 = "TestUser2";
  public final static String TEST_PASSWORD2 = "Password1234";
  public final static String TEST_PROFILE_IMAGE2 = "https://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/img_640x640.jpg";
  public final static User TEST_USER2 = new User(TEST_ID2, TEST_EMAIL2, TEST_NICKNAME2, TEST_PASSWORD1, TEST_PROFILE_IMAGE2, "", 0L, UserRole.USER);

  public final static String TEST_UPDATE_INTRODUCTION = "updated introduction";
  public final static String TEST_UPDATE_NICKNAME = "UpdatedNickname";
  public final static String TEST_UPDATE_PASSWORD = "UpdatedPassword";
  public final static String TEST_UPDATE_PROFILEIMAGE = "https://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/updated_img_640x640.jpg";
  public final static UserUpdateRequestDto TEST_USER_UPDATE_REQUEST_DTO =
      new UserUpdateRequestDto(TEST_UPDATE_INTRODUCTION, TEST_UPDATE_NICKNAME, TEST_UPDATE_PROFILEIMAGE, TEST_UPDATE_PASSWORD, TEST_UPDATE_PASSWORD);
  public final static User TEST_UPDATED_USER = new User(TEST_ID1, TEST_EMAIL1, TEST_UPDATE_NICKNAME, TEST_UPDATE_PASSWORD, TEST_UPDATE_PROFILEIMAGE, TEST_UPDATE_INTRODUCTION, 0L, UserRole.USER);

  private static final String TEST_EMAIL3 = "TestUser3@email.com";

  // Auth
  public final static SignupRequestDto SIGNUP_REQUEST_DTO1 = new SignupRequestDto(TEST_NICKNAME1, TEST_EMAIL1, TEST_PASSWORD1, TEST_PASSWORD1);
  public final static SignupRequestDto SIGNUP_REQUEST_DTO2 = new SignupRequestDto(TEST_NICKNAME2, TEST_EMAIL2, TEST_PASSWORD2, TEST_PASSWORD2);
  public final static SignupRequestDto SIGNUP_REQUEST_DTO_VALIDATION_FAIL = new SignupRequestDto(TEST_EMAIL1, TEST_PASSWORD1, TEST_NICKNAME1, TEST_NICKNAME2);
  public final static SignupRequestDto SIGNUP_REQUEST_DTO_CONFIRM_PASSWORD_FAIL = new SignupRequestDto(TEST_NICKNAME1, TEST_EMAIL3, TEST_PASSWORD2, TEST_PASSWORD1);
  public final static LoginRequestDto LOGIN_REQUEST_DTO1 = new LoginRequestDto(TEST_EMAIL1, TEST_PASSWORD1);
  public final static LoginRequestDto LOGIN_REQUEST_DTO2 = new LoginRequestDto(TEST_EMAIL2, TEST_PASSWORD2);
  public final static LoginRequestDto LOGIN_REQUEST_DTO_NOT_EXISTING_USER = new LoginRequestDto(TEST_EMAIL3, TEST_PASSWORD1);
  public final static LoginRequestDto LOGIN_REQUEST_DTO_WRONG_PASSWORD = new LoginRequestDto(TEST_EMAIL2,
      TEST_PASSWORD1 + TEST_PASSWORD1);
  public static final TestingAuthenticationToken TEST_AUTHENTICATION = new TestingAuthenticationToken(TEST_USER1, null, UserRole.USER.name());

}
