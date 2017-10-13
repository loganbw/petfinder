package com.logan.petfinder.Dao;

import com.logan.petfinder.models.Pet;
import com.logan.petfinder.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetDao extends CrudRepository<Pet, Long> {
    List<Pet> findAllByUser(User user);
}
