package com.example.wishes.service;

import com.example.wishes.exception.BadRequestException;
import com.example.wishes.exception.ResourceNotFoundException;
import com.example.wishes.model.User;
import com.example.wishes.model.Wish;
import com.example.wishes.model.WishList;
import com.example.wishes.repository.UserRepository;
import com.example.wishes.repository.WishListRepository;
import com.example.wishes.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishesService {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private UserRepository userRepository;

    //helper methods : Exception handling
    private void validateTitle(String title, String entityName) {
        if (title == null || title.trim().isEmpty()) {
            throw new BadRequestException(entityName + " title cannot be empty");
        }
    }

    private User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Username not found with username: " + username));
    }

    private WishList getWishListAndValidateOwnership(int wishListId, User user){
        WishList wishList = wishListRepository.findById(wishListId)
                .orElseThrow(() -> new ResourceNotFoundException("Wish list not found on id: " + wishListId));
        if(!wishList.getUser().equals(user)){
            throw new BadRequestException("Wish list does not belong to current user");
        }
        return wishList;
    }

    private Wish getWishAndValidateWishList(int wishId, WishList wishList){
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(()-> new ResourceNotFoundException("Wish not found on id: " + wishId));
        if(!wish.getWishList().equals(wishList)){
            throw new BadRequestException("Wish id: " + wishId + " is not from id: "+ wishList.getId() + " wish list");
        }
        return wish;
    }



    //get all wish lists
    public List<WishList> getAllWishLists(String username) {
        User user = getUserByUsername(username);
        return wishListRepository.findByUser(user);
    }

    //get wish list by id
    public WishList getWishListByID(String username, int id){
        User user = getUserByUsername(username);
        return getWishListAndValidateOwnership(id, user);
    }

    //create a wish list
    public WishList createWishList(String username, String title){
        validateTitle(title, "Wish List");
        User user = getUserByUsername(username);

        WishList wishList = new WishList();
        wishList.setUser(user);
        wishList.setTitle(title);
        return wishListRepository.save(wishList);
    }

    //update wish list
    public WishList updateWishList(String username, int idToUpdate, String newTitle){
        validateTitle(newTitle, "Wish List");
        User user = getUserByUsername(username);
        WishList wishListToUpdate = getWishListAndValidateOwnership(idToUpdate, user);
        wishListToUpdate.setTitle(newTitle);
        return wishListRepository.save(wishListToUpdate);
    }

    //delete wish list
    public void deleteWishList(String username, int id){
        User user = getUserByUsername(username);
        WishList wishList = getWishListAndValidateOwnership(id, user);

        wishListRepository.delete(wishList);
    }


    //get all wishes in a specific wish list
    public List<Wish> getAllWishes(String username, int wishListId){
        WishList wishList = getWishListByID(username, wishListId);
        return wishList.getWishes();
    }

    //create a wish inside a specific wish list
    public Wish createWish(String username, int wishListId, String title, Boolean isComplete){
        validateTitle(title, "Wish");

        WishList wishList = getWishListByID(username, wishListId);

        Wish wish = new Wish(title, isComplete);
        wish.setWishList(wishList);
        return wishRepository.save(wish);
    }

    //update a wish inside a specific wish list
    public Wish updateWish(String username, int wishListId, int wishId, String newTitle, boolean isCompleted) {
        validateTitle(newTitle, "Wish");
        WishList wishList = getWishListByID(username, wishListId);
        Wish wishToUpdate = getWishAndValidateWishList(wishId, wishList);

        wishToUpdate.setTitle(newTitle);
        wishToUpdate.setCompleted(isCompleted);

        return wishRepository.save(wishToUpdate);
    }

    //mark a wish completed
    public Wish markWishCompleted(String username, int wishListId, int wishId, boolean isCompleted) {
        WishList wishList = getWishListByID(username, wishListId);
        Wish wishToUpdate = getWishAndValidateWishList(wishId, wishList);

        wishToUpdate.setCompleted(isCompleted);

        return wishRepository.save(wishToUpdate);
    }

    // Delete a specific To-Do
    public void deleteWish(String username, int wishListId, int wishId) {
        WishList wishList = getWishListByID(username, wishListId);
        Wish wish = getWishAndValidateWishList(wishId, wishList);
        wishRepository.delete(wish);
    }

}
