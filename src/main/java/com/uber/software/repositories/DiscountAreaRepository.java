package com.uber.software.repositories;

import com.uber.software.models.DiscountArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountAreaRepository extends JpaRepository<DiscountArea, String> {
}
