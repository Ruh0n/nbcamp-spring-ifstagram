package com.nbcampif.ifstagram.domain.report.repository;

import com.nbcampif.ifstagram.domain.report.entity.Report;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

  List<Report> findAllByToUserId(Long reportId);

}
