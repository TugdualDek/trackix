package com.juniorisep.trackix.repository;

import com.juniorisep.trackix.model.DataMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataMailRepository extends JpaRepository<DataMail, Integer> {
}
