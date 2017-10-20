package com.logan.petfinder.Dao;

import com.logan.petfinder.models.Zip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipDao extends CrudRepository<Zip,Long> {
    Zip findByZipcode(String zipcode);

}
