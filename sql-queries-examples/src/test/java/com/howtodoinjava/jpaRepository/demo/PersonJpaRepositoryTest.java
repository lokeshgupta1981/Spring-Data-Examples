package com.howtodoinjava.jpaRepository.demo;

import com.howtodoinjava.jpaRepository.demo.model.Person;
import com.howtodoinjava.jpaRepository.demo.repository.PersonJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("/test-data.sql")
public class PersonJpaRepositoryTest {

  @Autowired
  PersonJpaRepository repository;

  @Test
  void findAllPersons_should_pass() {

    List<Person> persons = repository.findAll();
    assertThat(persons).isNotEmpty();
    assertThat(persons).hasSize(5);
  }
}
