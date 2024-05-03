package com.howtodoinjava.jdbcClient.demo;

import com.howtodoinjava.jdbcClient.demo.repository.PersonJdbcClientRepository;
import com.howtodoinjava.jdbcClient.demo.model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ImportAutoConfiguration(JdbcClientAutoConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql("/test-data.sql")
public class PersonJdbcClientRepositoryTest {

  @Autowired
  DataSource dataSource;
  PersonJdbcClientRepository repository;
  JdbcClient jdbcClient;

  @BeforeAll
  void setUp() {
    repository = new PersonJdbcClientRepository(dataSource);
    jdbcClient = JdbcClient.create(dataSource);
  }

  @Test
  void findAllPersons_should_pass() {
    List<Person> persons = repository.findAll();
    assertThat(persons).isNotEmpty();
    assertThat(persons).hasSize(5);
  }

  @Test
  void findAllPersonsByFirstName_should_pass() {
    List<Person> persons = repository.findAllByFirstName("Al");
    assertThat(persons).isNotEmpty();
    assertThat(persons).hasSize(1);
    assertThat(persons.get(0).getFirstName()).isEqualTo("Alex");
  }

  @Test
  void findPerson_by_id_should_pass() {
    Person person = new Person(null, "Clark", "Kent", Instant.now());
    Person newPerson = repository.save(person);
    assertThat(newPerson.getId()).isNotNull();

    Optional<Person> personOptional = repository.findById(newPerson.getId());
    assertThat(personOptional).isPresent();
    assertThat(personOptional.get().getId()).isEqualTo(newPerson.getId());
  }

  /*@Test
  void findAllPersons_should_pass() {
    List<Person> persons = repository.findAll();
    assertThat(persons).isNotEmpty();
    assertThat(persons).hasSize(3 );
  }

  @Test
  void addNewPerson_should_pass() {
    Person person = new Person(null, "Clark", "Kent", Instant.now());
    Person newPerson = repository.save(person);
    assertThat(newPerson.id()).isNotNull();
  }

  @Test
  void findPerson_by_id_should_pass() {
    Person person = new Person(null, "Clark", "Kent", Instant.now());
    Person newPerson = repository.save(person);
    assertThat(newPerson.id()).isNotNull();

    Optional<Person> personOptional = repository.findById(newPerson.id());
    assertThat(personOptional).isPresent();
    assertThat(personOptional.get().id()).isEqualTo(newPerson.id());
  }*/

  @Test
  void testPositionalParameters(){

    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "insert into person(first_name, last_name, created_at) values (:firstName, :lastName, :createdAt)";

    jdbcClient.sql(sql)
      .param("firstName", "Alex")
        .param("lastName", "Dave")
        .param("createdAt", Timestamp.from(Instant.now()))
        .update(keyHolder);

    Map<String, ?> paramMap = Map.of(
      "firstName", "Alex",
      "lastName", "Dave",
      "createdAt", Timestamp.from(Instant.now())
    );

    jdbcClient.sql(sql)
      .params(paramMap)
      .update(keyHolder);

    Person person = new Person(null, "Clark", "Kent", Instant.now());

    jdbcClient.sql(sql)
      .paramSource(person)
      .update(keyHolder);

    Long id = keyHolder.getKeyAs(Long.class);
    assertThat(id).isNotNull();
  }

  @Test
  void test_query_operation(){

    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "insert into person(first_name, last_name, created_at) values (:firstName, :lastName, :createdAt)";

    jdbcClient.sql(sql)
      .param("firstName", "Alex")
      .param("lastName", "Dave")
      .param("createdAt", Timestamp.from(Instant.now()))
      .update(keyHolder);

    String querySql = "select id, first_name, last_name, created_at from person where id = :id";
    Optional<Person> personOptional = jdbcClient.sql(querySql).param("id", keyHolder.getKey()).query(Person.class).optional();
    assertThat(personOptional.get()).isNotNull();
    System.out.println(personOptional.get());
  }
}
