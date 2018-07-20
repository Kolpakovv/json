package com.jsonpostgres.repositories;


import com.jsonpostgres.entities.Vk;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VkRepository extends CrudRepository<Vk, Long> {
    List <Vk> findByEmail(String email);

}