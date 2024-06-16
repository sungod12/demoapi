package com.crudapp.demoapi.repository;

import com.crudapp.demoapi.model.CryptoDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoApiRepository extends JpaRepository<CryptoDetails, Long> {
}
