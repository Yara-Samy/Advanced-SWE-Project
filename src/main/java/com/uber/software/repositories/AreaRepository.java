package com.uber.software.repositories;

import com.uber.software.models.AppUser;
import com.uber.software.models.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {
    List<Area> findByDriver(AppUser driver);
}
