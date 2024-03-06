package com.nbcampif.ifstagram.domain.post.dto;

import com.nbcampif.ifstagram.domain.post.entity.Post;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PostResponseDto {

  private final String title;
  private final String content;
  private final String postImg;
  private final List<PostResponseDto> postList = new ArrayList<>();

  public PostResponseDto(Post post, String postImg) {
    this.title = post.getTitle();
    this.content = post.getContent();
    this.postImg = postImg;
  }
}
