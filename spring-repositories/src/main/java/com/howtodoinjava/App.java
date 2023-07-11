package com.howtodoinjava;

import com.howtodoinjava.demo.model.Item;
import com.howtodoinjava.demo.repository.ItemRepository;
import java.util.List;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class App implements CommandLineRunner {

  @Autowired
  ItemRepository itemRepository;

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    for (int i = 1; i <= 3; i++){
      itemRepository.save(new Item(Long.valueOf(i), "Item " + i));
    }

    List<Item> items = itemRepository.findAll();
    log.info("All Items : " + items);
  }
}
