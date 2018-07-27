package com.jsonpostgres.repositories;


import com.jsonpostgres.entities.FB;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FbRepository extends CrudRepository<FB, Long> {
    List <FB> findByEmail(String email);

}