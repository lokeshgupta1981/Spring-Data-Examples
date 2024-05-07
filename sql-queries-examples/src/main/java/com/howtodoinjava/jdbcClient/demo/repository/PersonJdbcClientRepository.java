package com.howtodoinjava.jdbcClient.demo.repository;

import com.howtodoinjava.jdbcClient.demo.repository.mapper.PersonRowMapper;
import com.howtodoinjava.jdbcClient.demo.model.Person;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonJdbcClientRepository {

  private final JdbcClient jdbcClient;
  public PersonRowMapper personRowMapper = PersonRowMapper.getInstance();

  public PersonJdbcClientRepository(DataSource dataSource) {
    this.jdbcClient = JdbcClient.create(dataSource);
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
    jdbcClient.sql(sql).param("first_name", person.getFirstName()).param("last_name", person.getLastName()).param("created_at", Timestamp.from(person.getCreatedAt())).update(keyHolder);

    return new Person(keyHolder.getKeyAs(Long.class), person.getFirstName(), person.getFirstName(), person.getCreatedAt());
  }

  public int update(Person person) {
    String sql = "update person set first_name = ?, last_name = ? where id = ?";
    return jdbcClient.sql(sql).param(1, person.getFirstName()).param(2, person.getFirstName()).param(3, person.getId()).update();
  }

  public int delete(Long id) {
    String sql = "delete from person where id = ?";
    return jdbcClient.sql(sql).param(1, id).update();
  }

  // LIKE Operator

  public List<Person> findAllByFieldNameContaining(String fieldName, String searchTerm) {
    String sql = "select id, first_name, last_name, created_at from person where " + fieldName + " like concat('%', :searchTerm,'%')";
    return jdbcClient.sql(sql)
        .param("searchTerm", searchTerm)
        .query(personRowMapper)
        .list();
  }

  public List<Person> findAllByFieldNameStartsWith(String fieldName, String searchTerm) {
    String sql = "select id, first_name, last_name, created_at from person where " + fieldName + " like concat('%', :searchTerm)";
    return jdbcClient.sql(sql)
        .param("searchTerm", searchTerm)
        .query(personRowMapper)
        .list();
  }

  public List<Person> findAllByFieldNameEndsWith(String fieldName, String searchTerm) {
    String sql = "select id, first_name, last_name, created_at from person where " + fieldName + " like concat(:searchTerm, '%')";
    return jdbcClient.sql(sql)
        .param("searchTerm", searchTerm)
        .query(personRowMapper)
        .list();
  }

  public List<Person> findAllByFirstNameContaining(String searchTerm) {
    String sql = """
        select id, first_name, last_name, created_at \
        from person \
        where first_name like concat('%', :searchTerm,'%')""";
    return jdbcClient.sql(sql)
        .param("searchTerm", searchTerm)
        .query(personRowMapper)
        .list();
  }
}
