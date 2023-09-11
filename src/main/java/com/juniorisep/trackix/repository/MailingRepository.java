package com.juniorisep.trackix.repository;

import com.juniorisep.trackix.model.Mailing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailingRepository extends JpaRepository<Mailing, Integer> {
}
