package com.juniorisep.trackix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.juniorisep.trackix.model.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
}
