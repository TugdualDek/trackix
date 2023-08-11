package com.juniorisep.trackix.repository;

import com.juniorisep.trackix.model.DataLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataLinkRepository extends JpaRepository<DataLink, Integer> {
}
