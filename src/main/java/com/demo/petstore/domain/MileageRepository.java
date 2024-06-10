package com.demo.petstore.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MileageRepository extends JpaRepository<Mileage, Long>{
    
}
