package com.howtodoinjava.demo.elasticsearch.service;

import com.howtodoinjava.demo.elasticsearch.entities.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.erhlc.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeOperationsServiceTest {

  //@Autowired DO NOT Autowire
  private EmployeeOperationsService employeeOperationsService;

  @Container
  public static GenericContainer<?> elasticsearchContainer = new GenericContainer<>(
    "docker.elastic.co/elasticsearch/elasticsearch:8.10.4")
    .withExposedPorts(9200)
    .withEnv("discovery.type", "single-node")
    .withEnv("xpack.security.enabled", "false");

  @BeforeAll
  void setup() {
    ClientConfiguration clientConfiguration = ClientConfiguration
        .builder()
        .connectedTo(elasticsearchContainer.getContainerIpAddress() + ":" + elasticsearchContainer.getMappedPort(9200))
        .build();
    /*ElasticsearchOperations elasticsearchOperations = new ElasticsearchRestTemplate();
    this.employeeOperationsService = new ElasticsearchRestTemplate(client);*/
  }

  @Test
  void createEmployee() {
    Employee employee = new Employee();
    employee.setName("John");
    employee.setSalary(20000);

    Employee savedEmployee = employeeOperationsService.createEmployee(employee);

    assertNotNull(savedEmployee);
    assertNotNull(savedEmployee.getEmployeeId());

    employeeOperationsService.deleteEmployee(savedEmployee.getEmployeeId());
  }

  @Test
  void updateEmployee() {

    Employee employee = new Employee();
    employee.setName("John");
    employee.setSalary(20000);

    Employee savedEmployee = employeeOperationsService.createEmployee(employee);
    assertNotNull(savedEmployee);
    assertNotNull(savedEmployee.getEmployeeId());

    savedEmployee.setName("John Doe");
    Employee updatedEmployee = employeeOperationsService.updateEmployee(savedEmployee);
    assertEquals("John Doe", updatedEmployee.getName());

    employeeOperationsService.deleteEmployee(savedEmployee.getEmployeeId());
  }

  @Test
  void getEmployee() throws InterruptedException {
    Employee employee = new Employee();
    employee.setName("John");
    employee.setSalary(20000);

    Employee savedEmployee = employeeOperationsService.createEmployee(employee);

    Thread.sleep(1000);
    Employee fetchedEmployee = employeeOperationsService.getEmployee(savedEmployee.getEmployeeId());

    assertNotNull(fetchedEmployee);
    assertEquals(savedEmployee.getEmployeeId(), fetchedEmployee.getEmployeeId());
    assertEquals(20000, fetchedEmployee.getSalary());
    assertEquals("John", fetchedEmployee.getName());

    employeeOperationsService.deleteEmployee(savedEmployee.getEmployeeId());
  }

  @Test
  void deleteEmployee() {

    Employee employee = new Employee();
    employee.setName("John");
    employee.setSalary(20000);
    Employee savedEmployee = employeeOperationsService.createEmployee(employee);

    employeeOperationsService.deleteEmployee(savedEmployee.getEmployeeId());

    Employee fetchedEmployee = employeeOperationsService.getEmployee(savedEmployee.getEmployeeId());
    assertNull(fetchedEmployee);
  }

  @Test
  void searchEmployeeWithSalaryBetween() throws InterruptedException {
    Employee employee = new Employee();
    employee.setName("John");
    employee.setSalary(20000);
    Employee john = employeeOperationsService.createEmployee(employee);


    Employee employee2 = new Employee();
    employee2.setName("Ronaldo");
    employee2.setSalary(30000);
    Employee ronaldo = employeeOperationsService.createEmployee(employee2);

    Thread.sleep(1000);
    List<Employee> fetchedEmployees = employeeOperationsService.searchEmployeeWithSalaryBetween(10000L, 40000L);

    employeeOperationsService.deleteEmployee(john.getEmployeeId());
    employeeOperationsService.deleteEmployee(ronaldo.getEmployeeId());

    assertEquals(2, fetchedEmployees.size());
  }

  @Test
  void searchStringQuery() throws InterruptedException {

    Employee employee = new Employee();
    employee.setName("John");
    employee.setSalary(20000);
    Employee john = employeeOperationsService.createEmployee(employee);

    Thread.sleep(1000);
    List<Employee> fetchedEmployees = employeeOperationsService.searchStringQuery("John");
    employeeOperationsService.deleteEmployee(john.getEmployeeId());

    assertEquals(1, fetchedEmployees.size());
  }

  @Test
  void getAllEmployeesBySalary() throws InterruptedException {

    Employee employee = new Employee();
    employee.setName("John");
    employee.setSalary(20000);
    Employee john = employeeOperationsService.createEmployee(employee);

    Employee employee2 = new Employee();
    employee2.setName("Ronaldo");
    employee2.setSalary(20000);
    Employee ronaldo = employeeOperationsService.createEmployee(employee2);

    Thread.sleep(1000);
    List<Employee> fetchedEmployees = employeeOperationsService.getAllEmployeesBySalary(20000);

    employeeOperationsService.deleteEmployee(john.getEmployeeId());
    employeeOperationsService.deleteEmployee(ronaldo.getEmployeeId());

    assertEquals(2, fetchedEmployees.size());
  }

  @Test
  void getEmployeeUsingScroll() throws InterruptedException {

    Employee employee = new Employee();
    employee.setName("John");
    employee.setSalary(20000);
    Employee john = employeeOperationsService.createEmployee(employee);

    Employee employee2 = new Employee();
    employee2.setName("Ronaldo");
    employee2.setSalary(20000);
    Employee ronaldo = employeeOperationsService.createEmployee(employee2);

    Thread.sleep(1000);
    List<Employee> fetchedEmployees = employeeOperationsService.getEmployeeUsingScroll();

    employeeOperationsService.deleteEmployee(john.getEmployeeId());
    employeeOperationsService.deleteEmployee(ronaldo.getEmployeeId());

    assertEquals(2, fetchedEmployees.size());
  }
}