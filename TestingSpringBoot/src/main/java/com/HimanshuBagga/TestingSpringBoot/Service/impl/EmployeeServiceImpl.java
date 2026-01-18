package com.HimanshuBagga.TestingSpringBoot.Service.impl;

import com.HimanshuBagga.TestingSpringBoot.DTO.EmployeeDTO;
import com.HimanshuBagga.TestingSpringBoot.Service.EmployeeService;
import com.HimanshuBagga.TestingSpringBoot.entities.EmployeeEntity;
import com.HimanshuBagga.TestingSpringBoot.exceptions.ResourceNotFoundException;
import com.HimanshuBagga.TestingSpringBoot.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;



    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employee =  employeeRepository.findById(id).orElseThrow(() ->{
            throw new ResourceNotFoundException("Employee Not found with Id : " + id);
        });
        return modelMapper.map(employee , EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() {
        return employeeRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        List<EmployeeEntity> employeeEntities = employeeRepository.findByEmail(employeeDTO.getEmail());
        if(!employeeEntities.isEmpty()) {
            throw new RuntimeException("Employee Already exists with email: " + employeeDTO.getEmail());
        }
        EmployeeEntity newEmployee = modelMapper.map(employeeDTO , EmployeeEntity.class);
        EmployeeEntity savedEmployee = employeeRepository.save(newEmployee);
        return modelMapper.map(savedEmployee , EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
        EmployeeEntity employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));

        if(!employee.getEmail().equals(employeeDTO.getEmail())){
            throw new RuntimeException("The email of the employee not found :");
        }

        employeeDTO.setId(null);// id should not change
        modelMapper.map(employeeDTO , employee); // map the new changed field from employeeDTO to employee entity

        EmployeeEntity savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee , EmployeeDTO.class);
    }

    @Override
    public void deleteEmployeeById(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));

        updates.forEach((fieldName, value) -> {
            Field field = ReflectionUtils.findField(EmployeeEntity.class, fieldName);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, employeeEntity, value);
            }
        });

        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }
}
