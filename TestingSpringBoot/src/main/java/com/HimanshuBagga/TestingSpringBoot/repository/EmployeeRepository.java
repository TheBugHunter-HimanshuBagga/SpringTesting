package com.HimanshuBagga.TestingSpringBoot.repository;

import com.HimanshuBagga.TestingSpringBoot.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public class EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

}
