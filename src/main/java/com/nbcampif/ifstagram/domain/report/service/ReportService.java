package com.nbcampif.ifstagram.domain.report.service;

import com.nbcampif.ifstagram.domain.report.dto.ReportRequestDto;
import com.nbcampif.ifstagram.domain.report.dto.ReportResponseDto;
import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.global.dto.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface ReportService {

  ResponseEntity<CommonResponse<ReportResponseDto>> reportUser(
      Long reportedUserId,
      User user,
      ReportRequestDto requestDto
  );

}
