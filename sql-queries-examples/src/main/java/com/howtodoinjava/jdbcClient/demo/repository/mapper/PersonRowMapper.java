package com.howtodoinjava.jdbcClient.demo.repository.mapper;

import com.howtodoinjava.jdbcClient.demo.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class PersonRowMapper implements RowMapper<Person> {

  private PersonRowMapper() {}

  private static final PersonRowMapper INSTANCE = new PersonRowMapper();
  public static PersonRowMapper getInstance() {
    return INSTANCE;
  }

  @Override
  public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new Person(
      rs.getLong("id"),
      rs.getString("first_name"),
      rs.getString("last_name"),
      getInstantFromTimestamp(rs.getTimestamp("created_at"))
    );
  }

  private Instant getInstantFromTimestamp(Timestamp timestamp) {
    return (timestamp != null) ? timestamp.toInstant() : null;
  }
}
