package com.HimanshuBagga.TestingSpringBoot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employee")// we defined the table name ourself
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeEntity {
    @Id // primary Key of a table each table needs a unique identifier
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDate dateOfJoining;
    private Boolean isActive;
    private String role;
    private Double Salary;
}
