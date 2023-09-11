package com.juniorisep.trackix.repository;

import com.juniorisep.trackix.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientsRepository extends JpaRepository<Recipient, Integer> {
}
