package com.vendormodule.Vendor.Module.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vendormodule.Vendor.Module.entity.ClientProjectInfo;

import java.util.List;

@Repository
public interface ClientProjectInfoRepository extends JpaRepository<ClientProjectInfo, Integer> {
    
    // Find projects by company ID
    @Query("SELECT p FROM ClientProjectInfo p WHERE p.clientCompanyInfo.id = :companyId ORDER BY p.createdAt DESC")
    List<ClientProjectInfo> findByCompanyId(@Param("companyId") Integer companyId);
    
    // Find projects by status
    @Query("SELECT p FROM ClientProjectInfo p WHERE p.status = :status ORDER BY p.createdAt DESC")
    List<ClientProjectInfo> findByStatus(@Param("status") String status);
    
    // Find projects by company ID and status
    @Query("SELECT p FROM ClientProjectInfo p WHERE p.clientCompanyInfo.id = :companyId AND p.status = :status ORDER BY p.createdAt DESC")
    List<ClientProjectInfo> findByCompanyIdAndStatus(@Param("companyId") Integer companyId, @Param("status") String status);
}
