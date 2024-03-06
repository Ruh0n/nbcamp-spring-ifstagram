package com.nbcampif.ifstagram.domain.user.dto;

import lombok.Getter;

@Getter
public class ReportReponseDto {

    private final String content;
    private final Long from_user_id;
    private final Long to_user_id;


    public ReportReponseDto(String content, Long from_user_id, Long to_user_id) {
        this.content = content;
        this.from_user_id = from_user_id;
        this.to_user_id = to_user_id;
    }
}
