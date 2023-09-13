package com.juniorisep.trackix.repository;

import com.juniorisep.trackix.model.Smtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmtpRepository extends JpaRepository<Smtp, Integer> {
}
