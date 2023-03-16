package com.howtodoinjava.app.service;

import com.howtodoinjava.app.model.User;
import com.howtodoinjava.app.repository.UserRepository;
import com.speedment.jpastreamer.application.JPAStreamer;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  UserRepository userRepository;
  JPAStreamer jpaStreamer;

  @Autowired
  public UserService(UserRepository userRepository, JPAStreamer jpaStreamer) {
    this.userRepository = userRepository;
    this.jpaStreamer = jpaStreamer;
  }

  public Iterable<User> saveAll(List<User> users) {
    return userRepository.saveAll(users);
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public Optional<User> getById(Long id) {
    return userRepository.findById(id);
  }

  public List<User> findAllUsers(){
    return jpaStreamer.stream(User.class).collect(Collectors.toList());
  }

  public List<User> findAllUsersWithStatus(Boolean status){
    return jpaStreamer.stream(User.class)
        .filter(u -> u.getActive() == status)
        .collect(Collectors.toList());
  }
}
