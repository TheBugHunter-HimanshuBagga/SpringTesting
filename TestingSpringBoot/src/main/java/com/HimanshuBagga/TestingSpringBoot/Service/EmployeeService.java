package com.HimanshuBagga.TestingSpringBoot.Service;

import com.HimanshuBagga.TestingSpringBoot.DTO.EmployeeDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeService {

    Optional<EmployeeDTO> getEmployeeById(Long id);

    List<EmployeeDTO> getAllEmployee();

    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO);

    boolean deleteEmployeeById(Long employeeId);

    EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates);
}
