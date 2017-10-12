package com.logan.petfinder.Dao;

import com.logan.petfinder.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, Long> {
    Role findByName(String roleName);
}
