package com.howtodoinjava.repository;

import com.howtodoinjava.model.Person;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository {

  private final JdbcClient jdbcClient;
  public PersonRowMapper personRowMapper = PersonRowMapper.getInstance();

  public PersonRepository(JdbcClient jdbcClient) {
    this.jdbcClient = jdbcClient;
  }

  public List<Person> findAll() {
    String sql = "select id, first_name, last_name, created_at from person";
    return jdbcClient.sql(sql).query(personRowMapper).list();
  }

  public Optional<Person> findById(Long id) {
    String sql = "select id, first_name, last_name, created_at from person where id = :id";
    return jdbcClient.sql(sql).param("id", id).query(personRowMapper).optional();
  }

  public Person save(Person person) {
    String sql = "insert into person(first_name, last_name, created_at) values (:first_name,:last_name,:created_at)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcClient.sql(sql)
      .param("first_name", person.firstName())
      .param("last_name", person.lastName())
      .param("created_at", Timestamp.from(person.createdAt()))
      .update(keyHolder);

    return new Person(keyHolder.getKeyAs(Long.class),
      person.firstName(),
      person.lastName(),
      person.createdAt());
  }

  public int update(Person person) {
    String sql = "update person set first_name = ?, last_name = ? where id = ?";
    return jdbcClient.sql(sql)
      .param(1, person.firstName())
      .param(2, person.lastName())
      .param(3, person.id())
      .update();
  }

  public int delete(Long id) {
    String sql = "delete from person where id = ?";
    return jdbcClient.sql(sql).param(1, id).update();
  }
}
