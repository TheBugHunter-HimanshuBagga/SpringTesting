package com.HimanshuBagga.TestingSpringBoot.repository;

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
    private EmployeeRepository employeeRepository;

    private EmployeeEntity employee;

    @BeforeEach
    void setUp(){
        employee = EmployeeEntity.builder()
                .id(1L)
                .name("Himanshu")
                .email("himanshu@gmail.com")
                .Salary(100.00)
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee() {
// Arrange , given
        employeeRepository.save(employee);
// Act , when
        List<EmployeeEntity> employeeList = employeeRepository.findByEmail(employee.getEmail());
// Assert , then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();
        assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail());
    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList(){
// given
        String email = "notPresent@gmail.com";
// when
        List<EmployeeEntity> employeeEntityList = employeeRepository.findByEmail(email);
// then
        assertThat(employeeEntityList).isNotNull();
        assertThat(employeeEntityList).isEmpty();
    }
}