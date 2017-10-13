package com.logan.petfinder.Dao;

import com.logan.petfinder.models.Pet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetDao extends CrudRepository<Pet, Long> {

}
