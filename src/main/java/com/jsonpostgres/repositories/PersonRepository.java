package com.jsonpostgres.repositories;

import com.jsonpostgres.entities.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
 List <Person> findByEmail(String email);

}
