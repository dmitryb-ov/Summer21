package com.rest.app.orionrestapplication.repository;

import com.rest.app.orionrestapplication.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
}
