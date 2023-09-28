package com.juniorisep.trackix.repository;

import com.juniorisep.trackix.model.Group;
import com.juniorisep.trackix.model.Target;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetRepository extends JpaRepository<Target, Integer> {
    Target findByEmailAndGroup(String email, Group group);
}
