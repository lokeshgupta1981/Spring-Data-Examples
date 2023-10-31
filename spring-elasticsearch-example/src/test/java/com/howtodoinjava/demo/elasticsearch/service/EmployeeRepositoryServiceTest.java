package com.howtodoinjava.demo.elasticsearch.service;

import com.howtodoinjava.demo.elasticsearch.entities.Employee;
import com.howtodoinjava.demo.elasticsearch.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.util.Assert;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeRepositoryServiceTest {

    private EmployeeRepositoryService employeeRepositoryService;

    @Container
    public static ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer(
            "docker.elastic.co/elasticsearch/elasticsearch:8.10.4")
            .withExposedPorts(9200)
            .withEnv("discovery.type", "single-node")
            .withEnv("xpack.security.enabled", "false");


    @Configuration
    @EnableElasticsearchRepositories(basePackages = "com.howtodoinjava.demo.elasticsearch")
    static class TestConfiguration extends ElasticsearchConfiguration {
        @Override
        public ClientConfiguration clientConfiguration() {

            elasticsearchContainer.start();
            Assert.notNull(elasticsearchContainer, "TestContainer is not initialized!");

            return ClientConfiguration.builder() //
                    .connectedTo(elasticsearchContainer.getContainerIpAddress() + ":" +
                            elasticsearchContainer.getMappedPort(9200)) //
                    .build();
        }
    }

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeAll
    public void setup() {
        this.employeeRepositoryService = new EmployeeRepositoryService(employeeRepository);
    }

    @AfterAll
    public void cleanup() {
        elasticsearchContainer.stop();
    }

    @Test
    void createEmployee() {
        Employee employee = new Employee();
        employee.setName("Bruce");
        employee.setSalary(30000);

        Employee savedEmployee = employeeRepositoryService.createEmployee(employee);

        assertNotNull(savedEmployee);
        assertNotNull(savedEmployee.getEmployeeId());

        employeeRepositoryService.deleteEmployee(savedEmployee.getEmployeeId());
    }

    @Test
    void updateEmployee() {

        Employee employee = new Employee();
        employee.setName("Clark");
        employee.setSalary(40000);

        Employee savedEmployee = employeeRepositoryService.createEmployee(employee);
        assertNotNull(savedEmployee);
        assertNotNull(savedEmployee.getEmployeeId());

        savedEmployee.setName("Clark Kent");
        Employee updatedEmployee = employeeRepositoryService.updateEmployee(savedEmployee);
        assertEquals("Clark Kent", updatedEmployee.getName());

        employeeRepositoryService.deleteEmployee(savedEmployee.getEmployeeId());
    }

    @Test
    void getEmployee() throws InterruptedException {
        Employee employee = new Employee();
        employee.setName("Bruce");
        employee.setSalary(20000);

        Employee savedEmployee = employeeRepositoryService.createEmployee(employee);

        Thread.sleep(1000);
        Employee fetchedEmployee = employeeRepositoryService.getEmployee(savedEmployee.getEmployeeId());

        assertNotNull(fetchedEmployee);
        assertEquals(savedEmployee.getEmployeeId(), fetchedEmployee.getEmployeeId());
        assertEquals(20000, fetchedEmployee.getSalary());
        assertEquals("Bruce", fetchedEmployee.getName());

        employeeRepositoryService.deleteEmployee(savedEmployee.getEmployeeId());

    }

    @Test
    void deleteEmployee() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setSalary(20000);
        Employee savedEmployee = employeeRepositoryService.createEmployee(employee);

        employeeRepositoryService.deleteEmployee(savedEmployee.getEmployeeId());

        Employee fetchedEmployee = employeeRepositoryService.getEmployee(savedEmployee.getEmployeeId());
        assertNull(fetchedEmployee);
    }


    @Test
    void searchEmployeeWithSalaryBetween() throws InterruptedException {
        Employee employee = new Employee();
        employee.setName("Bruce");
        employee.setSalary(20000);
        Employee john = employeeRepositoryService.createEmployee(employee);


        Employee employee2 = new Employee();
        employee2.setName("Clark");
        employee2.setSalary(30000);
        Employee ronaldo = employeeRepositoryService.createEmployee(employee2);

        Thread.sleep(1000);
        List<Employee> fetchedEmployees = employeeRepositoryService.searchEmployeeWithSalaryBetween(10000L, 40000L);

        employeeRepositoryService.deleteEmployee(john.getEmployeeId());
        employeeRepositoryService.deleteEmployee(ronaldo.getEmployeeId());

        assertEquals(2, fetchedEmployees.size());
    }

    @Test
    void searchStringQuery() throws InterruptedException {
        Employee employee = new Employee();
        employee.setName("Bruce");
        employee.setSalary(20000);
        Employee john = employeeRepositoryService.createEmployee(employee);

        Thread.sleep(1000);
        List<Employee> fetchedEmployees = employeeRepositoryService.getEmployeeByName("Bruce");
        employeeRepositoryService.deleteEmployee(john.getEmployeeId());

        assertEquals(1, fetchedEmployees.size());
    }

    @Test
    void getEmployeeByName() throws InterruptedException {

        Employee employee = new Employee();
        employee.setName("Bruce");
        employee.setSalary(20000);
        Employee john = employeeRepositoryService.createEmployee(employee);

        Employee employee2 = new Employee();
        employee2.setName("Clark");
        employee2.setSalary(20000);
        Employee ronaldo = employeeRepositoryService.createEmployee(employee2);

        Thread.sleep(1000);
        List<Employee> fetchedEmployees = employeeRepositoryService.searchSalaryQuery(20000L);

        employeeRepositoryService.deleteEmployee(john.getEmployeeId());
        employeeRepositoryService.deleteEmployee(ronaldo.getEmployeeId());

        assertEquals(2, fetchedEmployees.size());
    }
}