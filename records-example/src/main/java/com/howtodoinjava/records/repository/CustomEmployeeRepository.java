package com.howtodoinjava.records.repository;


import com.howtodoinjava.records.dto.CustomEmployeeRecord;

import java.util.List;

public interface CustomEmployeeRepository {
    List<CustomEmployeeRecord> findAllEmployees();
}