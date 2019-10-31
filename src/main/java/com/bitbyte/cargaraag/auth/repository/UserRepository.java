package com.bitbyte.cargaraag.auth.repository;

import com.bitbyte.cargaraag.auth.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUserName(String userName);
}
