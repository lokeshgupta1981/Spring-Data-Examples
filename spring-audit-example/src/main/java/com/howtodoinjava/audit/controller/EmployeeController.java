package com.howtodoinjava.audit.controller;

import com.howtodoinjava.audit.entity.Employee;
import com.howtodoinjava.audit.respository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/employee")
    private Employee createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee =  employeeRepository.save(employee);
        log.info("Details after creation: {}", savedEmployee);
        return savedEmployee;
    }

    @PutMapping("/employee")
    private Employee updateEmployee(@RequestBody Employee employee) {
        Employee updatedEmployee =  employeeRepository.save(employee);
        log.info("Details after update: {}", updatedEmployee);
        return updatedEmployee;
    }
}
