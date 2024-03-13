package com.nbcampif.ifstagram.domain.post.entity;

import com.nbcampif.ifstagram.domain.post.dto.PostRequestDto;
import com.nbcampif.ifstagram.domain.repost.dto.RepostRequestDto;
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
import org.hibernate.annotations.SQLRestriction;
import org.springframework.web.multipart.MultipartFile;

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

  public void updateRepost(RepostRequestDto requestDto, String postImage, Long postId) {
    this.title = requestDto.getTitle();
    this.content = requestDto.getContent();
    this.repostId = postId;
    this.likeCount = 0L;
    this.repostCount = 0L;
  }

  public void updateLike() {
    this.likeCount++;
  }

  public void updatePost(PostRequestDto requestDto) {
    this.title = requestDto.getTitle();
    this.content = requestDto.getContent();
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }

//  public Post(PostRequestDto requestDto, userDetails userDetails) {
//    this.title = requestDto.getTitle();
//    this.content = requestDto.getContent();
//    this.postImg = requestDto.getPostImg();
//  }
}
