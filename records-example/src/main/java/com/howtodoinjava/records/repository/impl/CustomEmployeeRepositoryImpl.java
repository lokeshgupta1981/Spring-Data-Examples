package com.howtodoinjava.records.repository.impl;

import com.howtodoinjava.records.dto.CustomEmployeeRecord;
import com.howtodoinjava.records.repository.CustomEmployeeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomEmployeeRepositoryImpl implements CustomEmployeeRepository {

  private final JdbcTemplate jdbcTemplate;

  public CustomEmployeeRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<CustomEmployeeRecord> findAllEmployees() {
    return jdbcTemplate.query(
        "SELECT id, name FROM employee",
        (rs, rowNum) -> new CustomEmployeeRecord(
            rs.getLong("id"),
            rs.getString("name")
        )
    );
  }
}