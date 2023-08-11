package com.juniorisep.trackix.repository;

import com.juniorisep.trackix.model.LinkTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<LinkTrack, Integer> {
}
