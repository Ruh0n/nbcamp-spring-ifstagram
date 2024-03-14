package com.nbcampif.ifstagram.domain.user.repository.entity;

import com.nbcampif.ifstagram.domain.admin.dto.UserForceUpdateRequestDto;
import com.nbcampif.ifstagram.domain.user.UserRole;
import com.nbcampif.ifstagram.domain.user.dto.UserUpdateRequestDto;
import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.global.entity.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@SQLRestriction(value = "deleted_at is NULL")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE user_id = ?")
public class UserEntity extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(unique = true)
  private String email;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String password;

  @Column
  private String profileImage;

  @Column
  private String introduction;

  @Column
  @Default
  private Long reportedCount = 0L;

  @Column
  @Default
  @Enumerated(value = EnumType.STRING)
  private UserRole role = UserRole.USER;

  public static UserEntity fromModel(User user) {
    return UserEntity.builder()
        .email(user.getEmail())
        .nickname(user.getNickname())
        .password(user.getPassword())
        .profileImage(user.getProfileImage())
        .introduction(user.getIntroduction())
        .reportedCount(user.getReportedCount())
        .role(user.getRole())
        .build();
  }

  public User toModel() {
    return User.builder()
        .userId(userId)
        .email(email)
        .nickname(nickname)
        .password(password)
        .profileImage(profileImage)
        .introduction(introduction)
        .reportedCount(reportedCount)
        .role(role)
        .build();
  }

  public void update(UserUpdateRequestDto requestDto) {
    if (requestDto.getNickname() != null) {
      this.nickname = requestDto.getNickname();
    }
    if (requestDto.getProfileImage() != null) {
      this.profileImage = requestDto.getProfileImage();
    }
    if (requestDto.getIntroduction() != null) {
      this.introduction = requestDto.getIntroduction();
    }
    if (requestDto.getPassword() != null) {
      this.password = requestDto.getPassword();
    }
  }

  public void update(UserForceUpdateRequestDto requestDto) {
    if (requestDto.getNickname() != null) {
      this.nickname = requestDto.getNickname();
    }
    if (requestDto.getProfileImage() != null) {
      this.profileImage = requestDto.getProfileImage();
    }
    if (requestDto.getIntroduction() != null) {
      this.introduction = requestDto.getIntroduction();
    }
    if (requestDto.getRole() != null) {
      this.role = requestDto.getRole();
    }
  }

  public void updateReportedCount() {
    this.reportedCount += 1;
  }

}
