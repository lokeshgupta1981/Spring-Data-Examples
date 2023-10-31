package com.howtodoinjava.audit;

import com.howtodoinjava.audit.entity.Employee;
import com.howtodoinjava.audit.respository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Main implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  @Autowired
  EmployeeRepository repository;

  @Override
  public void run(String... args) throws Exception {
    Employee employee = new Employee("Test Name");

    employee = repository.save(employee);
    log.info("After save create timestamp: " + employee.getCreatedOn());
    log.info("After save create timestamp: " + employee.getUpdatedOn());

    Thread.sleep(2000);

    employee.setName("New Name");
    employee = repository.save(employee);

    log.info("After save update timestamp: " + employee.getCreatedOn());
    log.info("After save update timestamp: " + employee.getUpdatedOn());
  }
}