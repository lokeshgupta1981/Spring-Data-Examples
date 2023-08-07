package com.howtodoinjava.records.controller;

import com.howtodoinjava.records.dto.CustomEmployeeRecord;
import com.howtodoinjava.records.dto.EmployeeRecord;
import com.howtodoinjava.records.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees/{name}")
    public List<EmployeeRecord> findEmployeeByName(@PathVariable("name") String name) {
        return employeeService.findEmployeeByName(name);
    }

    @GetMapping("/employees")
    public List<CustomEmployeeRecord> findAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("/employees/mapping")
    public List<?> findAllEmployeeUsingMapping() {
        return employeeService.findAllEmployeeUsingMapping();
    }

    @GetMapping("/employees/transformer")
    public List<?> findAllEmployeeUsingTransformer() {
        return employeeService.findAllEmployeeUsingTupleTransformer();
    }
    @GetMapping("/employee/{id}")
    public EmployeeRecord findEmployeeById(@PathVariable("id") Long id) {
        return employeeService.findEmployeeById(id);
    }

    @GetMapping("/employees/salary/{salary}")
    public List<EmployeeRecord> findAllEmployeeWithSalaryGreater(@PathVariable("salary") int salary) {
        return employeeService.findAllEmployeeWithSalaryGreater(salary);
    }
}