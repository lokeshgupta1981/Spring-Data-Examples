package com.howtodoinjava.cassandrademo;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@EnableCassandraRepositories
public interface BookRepository extends CassandraRepository<Book, Long> {

    @AllowFiltering
    Book findBookByTitle(String title);
}
