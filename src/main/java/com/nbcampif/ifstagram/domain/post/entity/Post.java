package com.nbcampif.ifstagram.domain.post.entity;

import com.nbcampif.ifstagram.domain.post.dto.PostRequestDto;
import com.nbcampif.ifstagram.global.entity.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor

public class Post extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String title;
  @Lob
  @Column(nullable = false)
  private String content;
  @Column(nullable = false, columnDefinition = "BIGINT default 0")
  private Long likeCount;
  @Column(nullable = false, columnDefinition = "BIGINT default 0")
  private Long repostCount;
  @Column(nullable = false)
  private Long userId;
  @Column
  private Long repostId;

  public Post(PostRequestDto requestDto, Long userId) {
    this.title = requestDto.getTitle();
    this.content = requestDto.getContent();
    this.likeCount = 0L;
    this.repostCount = 0L;
    this.userId = userId;
    this.repostId = 0L;
  }

  public Post(Post post, Long userId) {
    this.title = post.getTitle();
    this.content = post.getContent();
    this.likeCount = 0L;
    this.repostCount = 0L;
    this.userId = userId;
    this.repostId = post.getId();
  }

  public void updatePost(PostRequestDto requestDto) {
    this.title = requestDto.getTitle();
    this.content = requestDto.getContent();
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }

}
