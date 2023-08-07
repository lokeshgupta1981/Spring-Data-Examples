package com.howtodoinjava.records.repository;

import com.howtodoinjava.records.dto.EmployeeRecord;
import com.howtodoinjava.records.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<EmployeeRecord> findEmployeeByName(String Name);

    @Query("SELECT new com.howtodoinjava.records.dto.EmployeeRecord(e.id, e.name, e.salary) FROM Employee e WHERE e.id = :id")
    EmployeeRecord findEmployeeById(@Param("id") Long id);
}