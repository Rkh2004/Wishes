package com.example.wishes.service;

import com.example.wishes.model.Wish;
import com.example.wishes.model.WishList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WishesService {

    private final Map<Integer, WishList> wishLists= new HashMap<>();
    private int wishListCounter = 1;
    private int wishCounter = 1;


    //get all wish lists
    public List<WishList> getAllWishLists() {
        return new ArrayList<>(wishLists.values());
    }

    //get wish list by id
    public WishList getWishListByID(int id){
        return wishLists.get(id);
    }

    //create a wish list
    public WishList createWishList(String title){
        WishList wishList = new WishList(wishListCounter++, title, new HashMap<>());
        wishLists.put(wishList.getId(), wishList);
        return wishList;
    }

    //update wish list
    public WishList updateWishList(int idToUpdate, String newTitle){
        WishList wishToUpdate = wishLists.get(idToUpdate);
        if (wishToUpdate != null) {
            wishToUpdate.setTitle(newTitle);
            wishLists.put(idToUpdate, wishToUpdate);
        }
        return wishToUpdate;
    }

    //delete wish list
    public void deleteWishList(int id){
        wishLists.remove(id);
    }

    //get all wishes in a specific wish list
    public List<Wish> getAllWishes(int wishListId){
        WishList wishList = wishLists.get(wishListId);
        return new ArrayList<>(wishList.getWishes().values());
    }

    //create a wish inside a specific wish list
    public Wish createWish(int wishListId, String title, Boolean isComplete){
        WishList wishList = wishLists.get(wishListId);
        if(wishList == null){
            throw new RuntimeException("Wish List not found");
        }
        Wish newWish = new Wish(wishCounter++, title, isComplete);
        wishList.getWishes().put(newWish.getId(), newWish);
        return newWish;
    }

    //update a wish inside a specific wish list
    public Wish updateWish(int wishListId, int wishId, String newTitle, boolean isCompleted) {
        WishList wishList = wishLists.get(wishListId);
        if (wishList == null) {
            throw new RuntimeException("Wish List not found");
        }
        Wish wish = wishList.getWishes().get(wishId);
        if (wish == null) {
            throw new RuntimeException("To-Do not found");
        }
        wish.setTitle(newTitle);
        wish.setCompleted(isCompleted);
        return wish;
    }

    //mark a wish completed
    public Wish markWishCompleted(int wishListId, int wishId, boolean isCompleted) {
        WishList wishList = wishLists.get(wishListId);
        if (wishList == null) {
            throw new RuntimeException("Wish List not found");
        }
        Wish wish = wishList.getWishes().get(wishId);
        if (wish == null) {
            throw new RuntimeException("To-Do not found");
        }
        wish.setCompleted(isCompleted);
        return wish;
    }


    // Delete a specific To-Do
    public void deleteWish(int wishListId, int wishId) {
        WishList wishList = wishLists.get(wishListId);
        if (wishList == null) {
            throw new RuntimeException("Wish List not found");
        }
        wishList.getWishes().remove(wishId);
    }

}
