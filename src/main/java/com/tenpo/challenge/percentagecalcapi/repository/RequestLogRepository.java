package com.tenpo.challenge.percentagecalcapi.repository;

import com.tenpo.challenge.percentagecalcapi.model.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
}
