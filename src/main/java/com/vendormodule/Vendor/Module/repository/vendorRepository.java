package com.ceil.vendor.vendorportal.repository;

import com.ceil.vendor.vendorportal.model.vendor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; 

@Repository
public interface vendorRepository extends JpaRepository<vendor, Long> {

    Optional<vendor> findByEmail(String email);

    Optional<vendor> findByGstin(String gstin);

}
