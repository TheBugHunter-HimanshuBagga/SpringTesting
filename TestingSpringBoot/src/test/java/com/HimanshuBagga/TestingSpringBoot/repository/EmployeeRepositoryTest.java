package com.HimanshuBagga.TestingSpringBoot.repository;

import com.HimanshuBagga.TestingSpringBoot.DTO.EmployeeDTO;
import com.HimanshuBagga.TestingSpringBoot.Service.EmployeeService;
import com.HimanshuBagga.TestingSpringBoot.Service.impl.EmployeeServiceImpl;
import com.HimanshuBagga.TestingSpringBoot.TestContainerConfiguration;
import com.HimanshuBagga.TestingSpringBoot.entities.EmployeeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
//@SpringBootTest only for integration testing

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(TestContainerConfiguration.class) // use db provided by test container configuration
@ExtendWith(MockitoExtension.class)
class EmployeeRepositoryTest {

    @Mock
    private EmployeeRepository employeeRepository; // put this mock inside employeeService , creats a fack repository

    @Spy
    private ModelMapper modelMapper; // it will be stubbed by itself i needed

    @InjectMocks // mokito tries to inject dependency into employeeServiceImpl
    private EmployeeServiceImpl employeeService;

    private EmployeeEntity mockEmployee;
    private EmployeeDTO mockEmployeeDTO;
    @BeforeEach
    void setup(){
        mockEmployee = EmployeeEntity.builder() // fake / mock employee
                .id(1L)
                .email("himanshu@gmail.com")
                .name("Himanshu")
                .age(20)
                .build();

        mockEmployeeDTO = modelMapper.map(mockEmployee , EmployeeDTO.class);
    }


    @Test
    void testGetEmployeeById_WhenEmployyeIdIsPresemt_ThemReturnEmployeeDTO(){
//      assign
        Long id = mockEmployee.getId();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee));

//      act
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id); // get back the mocked employee
//      assert
        assertThat(employeeDTO).isNotNull();
        assertThat(employeeDTO.getId()).isEqualTo(id); // assertThat is used to VERIFY that your code produced the expected result.
        assertThat(employeeDTO.getEmail()).isEqualTo(mockEmployee.getEmail());

        verify(employeeRepository).findById(id); // was this method called on this mock
        verify(employeeRepository).save(null); // error

        verify(employeeRepository , times(2)).findById(id); // times , atLeast , atMost , only

    }

    @Test
    void testCreateNewEmployee_WhenValidEmployee_ThenCreateNewEmployee(){

        // assign
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(mockEmployee);
        // act

        EmployeeDTO employeeDTO = employeeService.createNewEmployee(mockEmployeeDTO);

        // assert

        assertThat(employeeDTO).isNotNull();
        assertThat(employeeDTO.getEmail()).isEqualTo(mockEmployeeDTO.getEmail());

        ArgumentCaptor<EmployeeEntity> employeeEntityArgumentCaptor = ArgumentCaptor.forClass(EmployeeEntity.class);
        verify(employeeRepository).save(employeeEntityArgumentCaptor.capture()); //capture the real object passed over

        EmployeeEntity captureEmployee = employeeEntityArgumentCaptor.getValue();
        assertThat(captureEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());
    }
}