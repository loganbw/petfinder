package com.logan.petfinder.Dao;

import com.logan.petfinder.models.Friend;
import com.logan.petfinder.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendDao extends CrudRepository<Friend, Long>{
    List<Friend> findAllByFriendnameAndConfirmedFalse(String friendname);
    Friend findByUserAndFriendname(User user, String friendName);
}
