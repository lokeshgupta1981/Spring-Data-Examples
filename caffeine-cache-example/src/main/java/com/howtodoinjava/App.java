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

  public static void main(String[] args) {

    SpringApplication.run(App.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Student student = studentService.save(new Student("Lokesh Gupta"));

    student = studentService.getById(student.getId()); //Hits the database
    System.out.println(student);
    student = studentService.getById(student.getId()); //Fetched from cache
    System.out.println(student);

    try {
      studentService.deleteById(1L);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    student = studentService.getById(student.getId()); //Fetched from cache
    System.out.println(student);
  }
}
