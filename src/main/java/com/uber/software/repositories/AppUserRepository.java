package com.uber.software.repositories;

import com.uber.software.enums.UserRole;
import com.uber.software.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    List<AppUser> findByRole(UserRole role);
}
