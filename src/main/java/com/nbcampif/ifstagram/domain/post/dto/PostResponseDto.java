package com.nbcampif.ifstagram.domain.post.dto;

import com.nbcampif.ifstagram.domain.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {

  private final String title;
  private final String content;
  private final String postImg;

  public PostResponseDto(Post post, String postImg) {
    this.title = post.getTitle();
    this.content = post.getContent();
    this.postImg = postImg;
  }

}
