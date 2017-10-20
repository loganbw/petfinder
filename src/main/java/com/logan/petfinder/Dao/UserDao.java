package com.logan.petfinder.Dao;

import com.logan.petfinder.models.User;
import com.logan.petfinder.models.Zip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends CrudRepository<User,Long> {
    User findByUsername(String username);
    User findById(long id);
    List<User> findByZip(Zip zip);
    List<User> findByZipAndUsernameIsNot(Zip zip, String username);

}
