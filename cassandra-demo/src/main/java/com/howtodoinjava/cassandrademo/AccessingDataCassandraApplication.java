package com.howtodoinjava.cassandrademo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class AccessingDataCassandraApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataCassandraApplication.class, args);
	}

	@Bean
	public CommandLineRunner clr(BookRepository bookRepository) {
		return args -> {

			// save books in the database
			bookRepository.saveAll(Arrays.asList(
					new Book(1L,"War and Peace", "Tolstoy"),
					new Book(2L,"Harry Potter", "Rowling, J.K."),
					new Book(3L,"Anna Karenina", "Tolstoy")
			));

			// select only on book
			Book harryPotter = bookRepository.findBookByTitle("Harry Potter");

			// modify the selected book
			harryPotter.setTitle("Harry Potter and the Philosopher's Stone");
			bookRepository.save(harryPotter);

			//delete the book with id 1
			bookRepository.deleteById(1L);

			//get all the books
			bookRepository.findAll().forEach(System.out::println);

		};

	}
}
