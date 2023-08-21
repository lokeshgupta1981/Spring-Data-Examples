package com.howtodoinjava.audit.respository;

import com.howtodoinjava.audit.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
