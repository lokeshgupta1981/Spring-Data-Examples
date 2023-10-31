package com.howtodoinjava.service;


import com.howtodoinjava.dao.StudentRepository;
import com.howtodoinjava.exception.RecordNotFoundException;
import com.howtodoinjava.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService {

  @Autowired
  private StudentRepository studentRepository;

  @Cacheable("StudentCache")
  public Student getById(Long id) {

    return studentRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException("Record not wound with id: " + id));
  }

  @CacheEvict(value = "StudentCache", key = "#id")
  public void deleteById(Long id) {

    //TODO: REMOVE IT. Its for Demo Only.
    if (id == 1) {
      // Simulate a failure scenario where student id is 1 and cannot be deleted.
      throw new RuntimeException("Student cannot be deleted. It has magic ID");
    }
    studentRepository.deleteById(id);
  }

  @CacheEvict(value = "StudentCache", key = "#student.id")
  public Student save(Student student) {
    return studentRepository.save(student);
  }
}
