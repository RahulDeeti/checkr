package com.org.checkr.repository;

import com.org.checkr.entity.AdverseAction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdverseActionRepository extends JpaRepository<AdverseAction, Long> {
}

