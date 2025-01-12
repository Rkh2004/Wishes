package com.example.wishes.service;

import com.example.wishes.model.Wish;
import com.example.wishes.model.WishList;
import com.example.wishes.repository.WishListRepository;
import com.example.wishes.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishesService {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private WishRepository wishRepository;


    //get all wish lists
    public List<WishList> getAllWishLists() {
        return wishListRepository.findAll();
    }

    //get wish list by id
    public WishList getWishListByID(int id){
        return wishListRepository.findById(id).orElse(null);
    }

    //create a wish list
    public WishList createWishList(String title){
        return wishListRepository.save(new WishList(title));
    }

    //update wish list
    public WishList updateWishList(int idToUpdate, String newTitle){
        WishList wishListToUpdate = wishListRepository.findById(idToUpdate).orElse(null);
        if (wishListToUpdate != null) {
            wishListToUpdate.setTitle(newTitle);
            return wishListRepository.save(wishListToUpdate);
        }
        return null;
    }

    //delete wish list
    public void deleteWishList(int id){
        wishListRepository.deleteById(id);
    }

    //get all wishes in a specific wish list
    public List<Wish> getAllWishes(int wishListId){
        WishList wishList = wishListRepository.findById(wishListId).orElseThrow(() -> new RuntimeException("Wish List not found"));;
        return wishList.getWishes();
    }

    //create a wish inside a specific wish list
    public Wish createWish(int wishListId, String title, Boolean isComplete){
        WishList wishList = wishListRepository.findById(wishListId).orElse(null);
        if(wishList == null){
            throw new RuntimeException("Wish List not found");
        }

        Wish wish = new Wish(title, isComplete);
        wish.setWishList(wishList);
        return wishRepository.save(wish);
    }

    //update a wish inside a specific wish list
    public Wish updateWish(int wishListId, int wishId, String newTitle, boolean isCompleted) {
        WishList wishList = wishListRepository.findById(wishListId).orElse(null);
        if (wishList == null) {
            throw new RuntimeException("Wish List not found");
        }
        Wish wishToUpdate = wishRepository.findById(wishId).orElse(null);
        if (wishToUpdate == null) {
            throw new RuntimeException("To-Do not found");
        }

        wishToUpdate.setTitle(newTitle);
        wishToUpdate.setCompleted(isCompleted);

        return wishRepository.save(wishToUpdate);
    }

    //mark a wish completed
    public Wish markWishCompleted(int wishListId, int wishId, boolean isCompleted) {
        WishList wishList = wishListRepository.findById(wishListId).orElse(null);
        if (wishList == null) {
            throw new RuntimeException("Wish List not found");
        }
        Wish wishToUpdate = wishRepository.findById(wishId).orElse(null);
        if (wishToUpdate == null) {
            throw new RuntimeException("To-Do not found");
        }

        wishToUpdate.setCompleted(isCompleted);

        return wishRepository.save(wishToUpdate);
    }

    // Delete a specific To-Do
    public void deleteWish(int wishListId, int wishId) {
        WishList wishList = wishListRepository.findById(wishListId).orElse(null);
        if (wishList == null) {
            throw new RuntimeException("Wish List not found");
        }
        wishRepository.deleteById(wishId);
    }

}
