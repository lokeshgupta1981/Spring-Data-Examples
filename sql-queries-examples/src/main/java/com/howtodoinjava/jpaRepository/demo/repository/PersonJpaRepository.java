package com.howtodoinjava.jpaRepository.demo.repository;

import com.howtodoinjava.jpaRepository.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonJpaRepository extends JpaRepository<Person, Long> {

  List<Person> findByFirstNameLike(String firstNamePart);
  List<Person> findByFirstNameContaining(String firstNamePart);
  List<Person> findByFirstNameIsContaining(String firstNamePart);
  List<Person> findByFirstNameContains(String firstNamePart);

  List<Person> findByFirstNameNotLike(String firstNamePart);
  /*List<Person> findByFirstNameNotStartsWith(String firstNameStart);*/

  List<Person> findByFirstNameStartsWith(String firstNameStart);
  List<Person> findByFirstNameEndsWith(String firstNameEnd);

  List<Person> findByFirstNameLikeIgnoreCase(String firstNamePart);
  List<Person> findByFirstNameStartsWithIgnoreCase(String firstNameStart);
  List<Person> findByFirstNameEndsWithIgnoreCase(String firstNameEnd);
}
