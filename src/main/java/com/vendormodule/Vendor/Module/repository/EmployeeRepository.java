package com.ceil.vendor.vendorportal.repository;

import com.ceil.vendor.vendorportal.model.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marks this interface as a Spring Data JPA repository
public interface EmployeeRepository extends JpaRepository<employee, Long> {

    // Custom method to find an Employee by their email
    // Spring Data JPA automatically implements this based on method naming conventions
    employee findByEmail(String email);
}
