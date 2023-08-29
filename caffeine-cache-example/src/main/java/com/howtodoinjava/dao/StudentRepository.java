package com.howtodoinjava.dao;

import com.howtodoinjava.model.Student;
import org.springframework.data.repository.ListCrudRepository;

public interface StudentRepository extends ListCrudRepository<Student, Long> {

}
