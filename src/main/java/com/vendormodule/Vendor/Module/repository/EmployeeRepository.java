package com.vendormodule.Vendor.Module.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vendormodule.Vendor.Module.model.employee;

@Repository // Marks this interface as a Spring Data JPA repository
public interface EmployeeRepository extends JpaRepository<employee, Long> {

    // Custom method to find an Employee by their email
    // Spring Data JPA automatically implements this based on method naming conventions
    employee findByEmail(String email);
}
