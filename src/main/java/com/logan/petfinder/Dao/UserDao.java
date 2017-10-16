package com.logan.petfinder.Dao;

import com.logan.petfinder.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User,Long> {
    User findByUsername(String username);
    User findById(long id);
}
