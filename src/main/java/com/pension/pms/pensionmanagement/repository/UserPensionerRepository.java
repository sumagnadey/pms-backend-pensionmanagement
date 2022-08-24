package com.pension.pms.pensionmanagement.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pension.pms.pensionmanagement.model.UserPensioner;

public interface UserPensionerRepository extends JpaRepository<UserPensioner, String>{
    Optional<UserPensioner> findByUsername(String username);
    Boolean existsByUsername(String username);

    // Last edit
    Optional<UserPensioner> findByAadhaarNumber(String aadhaarNumber);
    Boolean existsByAadhaarNumber(String aadhaarNumber);
}
