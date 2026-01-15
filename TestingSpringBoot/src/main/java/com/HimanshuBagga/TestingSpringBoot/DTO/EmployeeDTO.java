package com.HimanshuBagga.TestingSpringBoot.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    @NotBlank(message = "Name can't be blank")
    @Size(min = 3 , max = 10 , message = "number of characters in the name should be in the range of 3-10")
    private String name;
    @NotBlank(message = "Email can't be left blank")
    @Email(message = "Email should be a valid Email including {@gmail.com}")
    private String email;
    @NotNull(message = "Age can't be null")
    @Max(value = 80 , message = "80+ users can't access")
    @Min(value = 18 , message = "18- users can't access")
    private Integer age;
    @NotBlank(message = "Admin field can't be empty")
    @Pattern(regexp = "^(ADMIN|USER)$" , message = "Role of Employee")
    private String role;
    @NotNull(message = "Salary of Employee cannot be blank")
    @Positive(message = "Salary of EMployee should be positive")
    @Digits(integer = 6 , fraction = 2 , message = "The salary can be in ******.**")
    @DecimalMax(value = "100.50") // minimum should be this and not smaller then this
    private Double Salary;
    @PastOrPresent(message = "Date of joining field should not be in future")
    private LocalDate dateOfJoining;
    @AssertTrue(message = "Employee should be active")
    private Boolean isActive;
}
