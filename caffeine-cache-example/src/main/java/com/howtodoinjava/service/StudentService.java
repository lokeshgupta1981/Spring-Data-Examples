package com.howtodoinjava.service;


import com.howtodoinjava.dao.StudentRepository;
import com.howtodoinjava.exception.RecordNotFoundException;
import com.howtodoinjava.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  @Autowired
  private StudentRepository studentRepository;

  @Cacheable("nameById")
  public Student getName(Long id) {

    return studentRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException("Record not wound with id: " + id));
  }
}
