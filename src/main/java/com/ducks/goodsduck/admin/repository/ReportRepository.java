package com.ducks.goodsduck.admin.repository;

import com.ducks.goodsduck.admin.model.entity.report.Report;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findBySenderId(Long senderId, Pageable pageable);

    List<Report> findBySenderNickName(String senderNickName, Pageable pageable);

    List<Report> findByReceiverId(Long receiverId, Pageable pageable);

    List<Report> findByReceiverNickName(String receiverNickName, Pageable pageable);
}
