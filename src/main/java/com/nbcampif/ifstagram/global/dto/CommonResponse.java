package com.nbcampif.ifstagram.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

  private String message;
  private T data;

  public static <T> ResponseEntity<CommonResponse<T>> ok(String message, T data) {
    return ResponseEntity.ok()
        .body(CommonResponse.<T>builder()
            .message(message)
            .data(data)
            .build());
  }

}
