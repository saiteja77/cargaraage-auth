package com.bitbyte.cargaraag.auth.repository;

import com.bitbyte.cargaraag.auth.entities.Roles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends CrudRepository<Roles, Long> {
    Optional<Roles> findByRole(String role);
}
