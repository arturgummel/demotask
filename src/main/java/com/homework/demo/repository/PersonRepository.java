package com.homework.demo.repository;

import com.homework.demo.entity.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findAll();

    Person getPersonByUsername(String username);
}
