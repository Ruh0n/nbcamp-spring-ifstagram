package com.nbcampif.ifstagram.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.nbcampif.ifstagram.domain.user.model.Follow;
import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.domain.user.repository.FollowRepository;
import com.nbcampif.ifstagram.domain.user.repository.RecentPasswordRepository;
import com.nbcampif.ifstagram.domain.user.repository.UserRepository;
import com.nbcampif.ifstagram.global.common.TestValues;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest implements TestValues {

  @Mock
  private UserRepository userRepository;
  @Mock
  private FollowRepository followRepository;
  @Mock
  private RecentPasswordRepository recentPasswordRepository;

  @InjectMocks
  private UserServiceImpl userService;


  @Nested
  @DisplayName("유저 신고")
  class reportUser {

    @Test
    void success() {
      // given
      User reportedUser = TEST_USER2;
      Long userId = reportedUser.getUserId();

      given(userRepository.findUser(any())).willReturn(Optional.of(reportedUser));

      // when
      userService.reportUser(userId);

      // then
      then(userRepository).should(times(1)).updateReportedCount(reportedUser);
    }

  }


  @Nested
  @DisplayName("유저 팔로우")
  class follow {

    @Test
    @DisplayName("success: 신규 팔로우")
    void success1() {
      // given
      User user = TEST_USER1;
      Long toUserId = TEST_ID2;

      given(followRepository.findByFromUserIdAndToUserId(any(), any())).willReturn(Optional.of(new Follow(user.getUserId(), toUserId)));
      // when
      userService.follow(user, toUserId);

      // then
      then(followRepository).should(times(1)).deleteFollow(any(Follow.class));
    }

    @Test
    @DisplayName("success: 이미 팔로우 중 -> 언팔로우")
    void success2() {
      // given
      User user = TEST_USER1;
      Long toUserId = TEST_USER2.getUserId();

      given(followRepository.findByFromUserIdAndToUserId(any(), any())).willReturn(Optional.empty());
      // when
      userService.follow(user, toUserId);

      // then
      then(followRepository).should(times(1)).saveFollow(user.getUserId(), toUserId);
    }

    @Test
    @DisplayName("fail: 자기 자신 팔로우")
    void fail1() {
      // given
      User user = TEST_USER1;
      Long toUserId = user.getUserId();

      // when & then
      assertThrows(IllegalArgumentException.class, () -> userService.follow(user, toUserId));
    }

  }

  @Nested
  @DisplayName("유저 업데이트")
  class updateUser {

    @Test
    void success() {
      // given
      User user = TEST_USER1;

      given(userRepository.findByEmailOrElseThrow(any())).willReturn(user);
      given(userRepository.updateUser(any(), any())).willReturn(TEST_UPDATED_USER);

      // when
      User updatedUser = userService.updateUser(TEST_USER_UPDATE_REQUEST_DTO, user);

      // then
      assertEquals(TEST_UPDATE_INTRODUCTION, updatedUser.getIntroduction());
      assertEquals(TEST_UPDATE_NICKNAME, updatedUser.getNickname());
      assertEquals(TEST_UPDATE_PROFILEIMAGE, updatedUser.getProfileImage());
      then(userRepository).should(times(1)).updateUser(any(), any());
    }

  }

}
