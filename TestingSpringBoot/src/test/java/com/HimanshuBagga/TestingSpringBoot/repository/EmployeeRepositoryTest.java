package com.HimanshuBagga.TestingSpringBoot.repository;

import com.HimanshuBagga.TestingSpringBoot.Service.EmployeeService;
import com.HimanshuBagga.TestingSpringBoot.TestContainerConfiguration;
import com.HimanshuBagga.TestingSpringBoot.entities.EmployeeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(TestContainerConfiguration.class)
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeService employeeService;

    void test
}