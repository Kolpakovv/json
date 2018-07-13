package com.jsonpostgres.repositories;

import com.jsonpostgres.entities.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
