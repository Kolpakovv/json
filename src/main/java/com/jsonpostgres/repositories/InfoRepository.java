package com.jsonpostgres.repositories;


import com.jsonpostgres.entities.Info;
import com.jsonpostgres.entities.Vk;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InfoRepository extends CrudRepository<Info, Long> {
    List <Info> findByEmail(String email);
    List <Info> findByAge(Integer age);

}
