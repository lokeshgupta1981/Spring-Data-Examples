package com.howtodoinjava.repository;

import com.howtodoinjava.model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ImportAutoConfiguration(JdbcClientAutoConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql("/test-data.sql")
public class PersonRepositoryTest {

  @Autowired
  JdbcClient jdbcClient;
  PersonRepository repository;

  @BeforeAll
  void setUp() {
    repository = new PersonRepository(jdbcClient);
  }

  @Test
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
  }
}
