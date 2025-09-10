package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PlantEntity;

@Repository
public interface PlantRepository extends JpaRepository<PlantEntity, Long> {
    
}
