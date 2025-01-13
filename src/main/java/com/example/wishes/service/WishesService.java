package com.example.wishes.service;

import com.example.wishes.exception.BadRequestException;
import com.example.wishes.exception.ResourceNotFoundException;
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

    //helper methods : Exception handling
    private void validateTitle(String title, String entityName) {
        if (title == null || title.trim().isEmpty()) {
            throw new BadRequestException(entityName + " title cannot be empty");
        }
    }

    private Wish getWishAndValidateWishList(int wishId, WishList wishList){
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(()-> new ResourceNotFoundException("Wish not found on id: " + wishId));
        if(wish.getWishList().getId() != wishList.getId()){
            throw new BadRequestException("Wish id: " + wishId + " is not from id: "+ wishList.getId() + " wish list");
        }
        return wish;
    }



    //get all wish lists
    public List<WishList> getAllWishLists() {

        return wishListRepository.findAll();
    }

    //get wish list by id
    public WishList getWishListByID(int id){

        return wishListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wish List not found with id : " + id));
    }

    //create a wish list
    public WishList createWishList(String title){
        validateTitle(title, "Wish List");
        return wishListRepository.save(new WishList(title));
    }

    //update wish list
    public WishList updateWishList(int idToUpdate, String newTitle){
        validateTitle(newTitle, "Wish List");
        WishList wishListToUpdate = getWishListByID(idToUpdate);
        wishListToUpdate.setTitle(newTitle);
        return wishListRepository.save(wishListToUpdate);
    }

    //delete wish list
    public void deleteWishList(int id){
        if(!wishListRepository.existsById(id)){
            throw new ResourceNotFoundException("Wish List not found on id : " + id);
        }
        wishListRepository.deleteById(id);
    }


    //get all wishes in a specific wish list
    public List<Wish> getAllWishes(int wishListId){
        WishList wishList = getWishListByID(wishListId);
        return wishList.getWishes();
    }

    //create a wish inside a specific wish list
    public Wish createWish(int wishListId, String title, Boolean isComplete){
        validateTitle(title, "Wish");

        WishList wishList = getWishListByID(wishListId);

        Wish wish = new Wish(title, isComplete);
        wish.setWishList(wishList);
        return wishRepository.save(wish);
    }

    //update a wish inside a specific wish list
    public Wish updateWish(int wishListId, int wishId, String newTitle, boolean isCompleted) {
        validateTitle(newTitle, "Wish");
        WishList wishList = getWishListByID(wishListId);
        Wish wishToUpdate = getWishAndValidateWishList(wishId, wishList);

        wishToUpdate.setTitle(newTitle);
        wishToUpdate.setCompleted(isCompleted);

        return wishRepository.save(wishToUpdate);
    }

    //mark a wish completed
    public Wish markWishCompleted(int wishListId, int wishId, boolean isCompleted) {
        WishList wishList = getWishListByID(wishListId);
        Wish wishToUpdate = getWishAndValidateWishList(wishId, wishList);

        wishToUpdate.setCompleted(isCompleted);

        return wishRepository.save(wishToUpdate);
    }

    // Delete a specific To-Do
    public void deleteWish(int wishListId, int wishId) {
        WishList wishList = getWishListByID(wishListId);
        Wish wish = getWishAndValidateWishList(wishId, wishList);
        wishRepository.delete(wish);
    }

}
