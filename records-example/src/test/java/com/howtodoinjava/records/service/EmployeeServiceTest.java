package com.howtodoinjava.records.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.howtodoinjava.records.dto.CustomEmployeeRecord;
import com.howtodoinjava.records.dto.EmployeeRecord;
import com.howtodoinjava.records.entity.Employee;
import com.howtodoinjava.records.repository.CustomEmployeeRepository;
import com.howtodoinjava.records.repository.EmployeeRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SuppressWarnings({"unused"})
class EmployeeServiceTest {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private CustomEmployeeRepository customEmployeeRepository;

  @BeforeEach
  public void setup() {
    Employee employee = new Employee();
    employee.setId(1L);
    employee.setName("John Doe");
    employee.setSalary(140000L);

    employeeRepository.save(employee);
  }

  @AfterEach
  public void cleanUp() {
    employeeRepository.deleteAll();
  }

  @Test
  void findEmployeeByName() {
    List<EmployeeRecord> fetchedEmployee = employeeRepository.findEmployeeByName("John Doe");

    assertNotNull(fetchedEmployee.get(0));
    assertEquals("John Doe", fetchedEmployee.get(0).name());
  }

  @Test
  void findAllEmployees() {
    List<CustomEmployeeRecord> fetchedEmployee = customEmployeeRepository.findAllEmployees();

    assertNotNull(fetchedEmployee.get(0));
    assertEquals("John Doe", fetchedEmployee.get(0).name());
  }

  @Test
  void findEmployeeById() {
    EmployeeRecord fetchedEmployee = employeeRepository.findEmployeeById(1L);
    assertNotNull(fetchedEmployee);
  }

  @Test
  void findAllEmployeeWithSalaryGreater() {
    List<EmployeeRecord> fetchedEmployee = employeeService.findAllEmployeeWithSalaryGreater(40000);

    assertNotNull(fetchedEmployee.get(0));
    assertEquals("John Doe", fetchedEmployee.get(0).name());
    assertEquals(140000L, fetchedEmployee.get(0).salary());
  }

  @Test
  void findAllEmployeeUsingMapping() {
    List<EmployeeRecord> fetchedEmployee = employeeService.findAllEmployeeUsingMapping();

    assertNotNull(fetchedEmployee.get(0));
    assertEquals("John Doe", fetchedEmployee.get(0).name());
  }

  @Test
  void findAllEmployeeUsingTupleTransformer() {
    List<EmployeeRecord> fetchedEmployee = employeeService.findAllEmployeeUsingTupleTransformer();

    assertNotNull(fetchedEmployee.get(0));
    assertEquals("John Doe", fetchedEmployee.get(0).name());
  }
}