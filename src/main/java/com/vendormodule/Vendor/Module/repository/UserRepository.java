package com.vendormodule.Vendor.Module.repository;

import com.vendormodule.Vendor.Module.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); }
