package com.uber.software.repositories;


import com.uber.software.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  RatingRepository extends JpaRepository <Rating, Integer> {

    List<Rating> findByDriver(String driver);
}
