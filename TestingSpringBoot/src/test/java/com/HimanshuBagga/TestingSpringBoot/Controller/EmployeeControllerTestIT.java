package com.HimanshuBagga.TestingSpringBoot.Controller;

import com.HimanshuBagga.TestingSpringBoot.DTO.EmployeeDTO;
import com.HimanshuBagga.TestingSpringBoot.TestContainerConfiguration;
import com.HimanshuBagga.TestingSpringBoot.entities.EmployeeEntity;
import com.HimanshuBagga.TestingSpringBoot.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebTestClient(timeout = "100000") // create and inject a webTestClient Bean for me
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // start full spring server -> loads controller,service,repository  -> starts embedded servers -> picks a random port
@Import(TestContainerConfiguration.class) // starts a real db using testcontainers
class EmployeeControllerTestIT {
    @Autowired
    private WebTestClient webTestClient; // postman inside ur test , sends get/post/put/delete

    @Autowired
    private EmployeeRepository employeeRepository; // first create some employee

    @Autowired
    private EmployeeEntity testEmployee;

    private EmployeeDTO testEmployeeDTO;

    @BeforeEach
    void setUp(){
        testEmployee = EmployeeEntity.builder() // fake / mock employee
                .id(1L)
                .email("himanshu@gmail.com")
                .name("Himanshu")
                .age(20)
                .build();

        testEmployeeDTO = EmployeeDTO.builder()
                .id(1L)
                .email("himanshu@gmail.com")
                .name("Himanshu")
                .age(20)
                .build();
        employeeRepository.deleteAll();
    }

    @Test
    void testGetEmployeeById_success(){
        EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);
        webTestClient.get()
                .uri("/employees/{employeeId}" , savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDTO.class)
                .isEqualTo(testEmployeeDTO);
//                .value(employeeDTO -> {
//                    assertThat(employeeDTO.getEmail()).isEqualTo(savedEmployee.getEmail())
//                })
    }

    @Test
    void testGetEmployeeById_Failure(){
        webTestClient.get()
                .uri("/employees/{employeeId}")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateNewEmployee_WhenEmployeeAlreadyExists_thenThrowException(){
        EmployeeEntity savedEmployee = employeeRepository.save(testEmployee); // save

        webTestClient.post()
                .uri("/employee")
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testCreateNewEmployee_WhenEmployeeDoesNotExists_thenCreateEmployee(){
        webTestClient.post()
                .uri("/employee")
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().isCreated() // checks response status is 201
                .expectBody()
                .jsonPath("$.email").isEqualTo(testEmployeeDTO.getEmail()) // jsonPath used to read values from the json body and verify them
                .jsonPath("$.name").isEqualTo(testEmployeeDTO.getName());
    }

    @Test
    void testUpdateEmployee_WhenEmployeeDoesNotExists_thenThrowException(){
        webTestClient.put()
                .uri("/employee/999")
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateEmployee_WhenAttemptingToUpdateTheEmail_thenThrowException(){
        EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);
        testEmployeeDTO.setName("Random name");
        testEmployeeDTO.setEmail("Random@gmail.com");
        webTestClient.put()
                .uri("/employee/{id}" ,  savedEmployee.getId())
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testUpdateEmployee_WhenEmployeeIdisValid_thenUpdateEmployee(){
        EmployeeEntity employee = employeeRepository.save(testEmployee);
        testEmployeeDTO.setName("Random");
        testEmployeeDTO.setAge(21);
        webTestClient.put()
                .uri("/employee/{id}")
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDTO.class)
                .isEqualTo(testEmployeeDTO);
    }

    @Test
    void testDeleteEmployee_WhenEmployeeDoesNotExists_thenThrowException(){
        webTestClient.delete()
                .uri("/elmployee/1")
                .exchange()
                .expectStatus().isNotFound();
    }

}



/*
    There are multiple enviroment where our test and deploy
    Dev -> developers | Test -> testers | Stage -> product Manager | Prod -> production enviroment

    each enviroment requires a setting that is specific to them. These enviroment host specific configuration called profiles.
    Use @Profile annotations to Load beans specific to profiles

*/