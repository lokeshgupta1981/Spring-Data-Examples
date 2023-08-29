package com.howtodoinjava;

import com.howtodoinjava.dao.StudentRepository;
import com.howtodoinjava.model.Student;
import com.howtodoinjava.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

  @Autowired
  StudentService studentService;

  @Autowired
  StudentRepository studentRepository;

  public static void main(String[] args) {

    SpringApplication.run(App.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Student student = studentRepository.save(new Student("Lokesh Gupta"));

    student = studentService.getName(student.getId()); //Hits the database
    student = studentService.getName(student.getId()); //Fetched from cache
  }
}
