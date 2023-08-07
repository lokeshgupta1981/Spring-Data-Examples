package com.howtodoinjava.records.service;

import com.howtodoinjava.records.dto.CustomEmployeeRecord;
import com.howtodoinjava.records.dto.EmployeeRecord;
import com.howtodoinjava.records.entity.Employee;
import com.howtodoinjava.records.repository.EmployeeRepository;
import com.howtodoinjava.records.repository.CustomEmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomEmployeeRepository customEmployeeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<EmployeeRecord> findEmployeeByName(String name) {
        return employeeRepository.findEmployeeByName(name);
    }

    public List<CustomEmployeeRecord> findAllEmployees() {
        return customEmployeeRepository.findAllEmployees();
    }

    public EmployeeRecord findEmployeeById(Long id) {
        return employeeRepository.findEmployeeById(id);
    }

    public List<EmployeeRecord> findAllEmployeeWithSalaryGreater(int salary) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeRecord> query = cb.createQuery(EmployeeRecord.class);
        Root<Employee> root = query.from(Employee.class);
        query.select(cb.construct(EmployeeRecord.class,
                root.get("id"), root.get("name"), root.get("salary")))
                .where(cb.gt(root.get("salary"), salary));
        return entityManager.createQuery(query).getResultList();
    }

    public List<EmployeeRecord> findAllEmployeeUsingMapping() {
        Query query = entityManager.createNativeQuery("SELECT * FROM employee", "EmployeeRecordMapping");
        return query.getResultList();
    }

    public List<EmployeeRecord> findAllEmployeeUsingTupleTransformer() {
        return entityManager.createQuery(" select e.id, e.name, e.salary from Employee e")
                .unwrap(org.hibernate.query.Query.class)
                .setTupleTransformer((objects, strings) -> {
                    int i = 0;
                    return new EmployeeRecord(
                            (Long) objects[i++],
                            (String) objects[i++],
                            (Long) objects[i++]);
                }).getResultList();


    }
}