package com.howtodoinjava;

import com.howtodoinjava.demo.model.Item;
import com.howtodoinjava.demo.repository.ItemRepository;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Log
public class App implements CommandLineRunner {

  final ItemRepository itemRepository;

  public App(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Override
  public void run(String... args) {
    for (int i = 1; i <= 3; i++){
      itemRepository.save(new Item((long) i, "Item " + i));
    }

    List<Item> items = itemRepository.findAll();
    log.info("All Items : " + items);
  }
}
