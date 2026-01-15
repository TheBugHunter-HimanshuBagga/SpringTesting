package com.HimanshuBagga.TestingSpringBoot.Controller;

import com.HimanshuBagga.TestingSpringBoot.DTO.EmployeeDTO;
import com.HimanshuBagga.TestingSpringBoot.Service.EmployeeService;
import com.HimanshuBagga.TestingSpringBoot.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EmployeeController {

    //    @GetMapping("/getMySuperSecretMessage")
//    public String getMySuperSecretMessage(){
//        return "Secret Message: @#$%^";
//    }

//    // Through Pathvariables
//    @GetMapping(path = "{employeeId}")
//    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId){
//        return new EmployeeDTO(employeeId , "Himanshu" , "Himanshu@Gmail.com" , 18 , LocalDate.of(2025,1,31) , true);
//    }
//
//    @GetMapping("/employee/{employeeId}/{name}/{email}")
//    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId , @PathVariable String name , @PathVariable String email){
//        return new EmployeeDTO(employeeId , name , email , 18 , LocalDate.of(2025,1,31) , false);
//    }
    //
    //    // Through RequestParam
    //    @GetMapping(path = "/employees")
    //    @GetMapping
    //    public String getAllEmployees(@RequestParam Integer age , @RequestParam String name){
    //        return "Hi age " + age + "Name" + name;
    //    }
    //    @PostMapping
    //    public String createNewEmployee(){
    //        return "Hello from POST";
    //    }
    //
    //    @PostMapping
    //    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
    //        inputEmployee.setId(100L);
    //        return inputEmployee;
    //    }
    //
    //    @PutMapping
    //    public String updateEmployee(){
    //        return "Hello from PUT";
    //    }

    // Below is the code of connecting persistance layer to repository layer

    //    private final EmployeeRepository employeeRepository; // connecting controller to repository
    //
    //    public EmployeeController(EmployeeRepository employeeRepository) {
    //        this.employeeRepository = employeeRepository;
    //    }
    //
    //    @GetMapping("/{employeeId}")
    //    public EmployeeEntity getEmployeeById(@PathVariable Long employeeId){
    //        return employeeRepository.findById(employeeId).orElse(null);
    //    }
    //
    //    @GetMapping
    //    public List<EmployeeEntity> getAllEmployee(){
    //        return employeeRepository.findAll();
    //    }
    //
    //    @PostMapping
    //    public EmployeeEntity createNewEmployee(@RequestBody EmployeeEntity employeeEntity){
    //        return employeeRepository.save(employeeEntity);
    //    }

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId) {
        Optional<EmployeeDTO> employeeDTO =  employeeService.getEmployeeById(employeeId);
        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                //                .orElse(ResponseEntity.notFound().build());
                .orElseThrow(() -> new ResourceNotFoundException("Employee not there with ID: " + employeeId));
    }

    //problem with this code below is that it works only for EmployeeController what if we have diffrent controller for this then we need to use global exception hence we need to declare it globally inside the advice section
    //    @ExceptionHandler(NoSuchElementException.class)
    //    public ResponseEntity<String> handleEmployeeNotFound(NoSuchElementException exception){
    //        return new ResponseEntity<>("Employee Not found " , HttpStatus.NOT_FOUND);
    //    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee(@RequestParam(required = false) Integer age,
                                                            @RequestParam(required = false) String name) {
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }



    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        EmployeeDTO savedEmployee = employeeService.createNewEmployee(employeeDTO);
        return new ResponseEntity<>(savedEmployee , HttpStatus.CREATED);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId, employeeDTO));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId) {
        //        return ResponseEntity.ok(employeeService.deleteEmployeeById(employeeId));
        boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
        if(gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@PathVariable Long employeeId , @RequestBody Map<String , Object> updates){
        return ResponseEntity.ok(employeeService.updatePartialEmployeeById(employeeId , updates));
    }
}
