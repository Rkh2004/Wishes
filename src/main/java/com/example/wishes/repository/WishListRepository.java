package com.example.wishes.repository;

import com.example.wishes.model.User;
import com.example.wishes.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {
    List<WishList> findByUser(User user);
}
