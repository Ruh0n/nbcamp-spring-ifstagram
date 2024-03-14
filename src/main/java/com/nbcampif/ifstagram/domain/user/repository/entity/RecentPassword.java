package com.nbcampif.ifstagram.domain.user.repository.entity;

import com.nbcampif.ifstagram.global.entity.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "recent_password")
public class RecentPassword extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String password;

  @Column
  private Long userId;

  public RecentPassword(String password, Long userId) {
    this.password = password;
    this.userId = userId;
  }

}
