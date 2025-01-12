package com.example.wishes.controller;

import com.example.wishes.model.Wish;
import com.example.wishes.model.WishList;
import com.example.wishes.service.WishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishesController {

    @Autowired
    private WishesService wishesService;


    // Get all Wish Lists
    @GetMapping
    public ResponseEntity<List<WishList>> getAllWishLists() {
        List<WishList> wishLists = wishesService.getAllWishLists();
        return ResponseEntity.ok(wishLists);
    }

    // Get a specific Wish List by ID
    @GetMapping("/{id}")
    public ResponseEntity<WishList> getWishListById(@PathVariable int id) {
        WishList wishList = wishesService.getWishListByID(id);
        if (wishList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(wishList);
    }

    // Create a new Wish List
    @PostMapping
    public ResponseEntity<WishList> createWishList(@RequestBody String title) {
        WishList wishList = wishesService.createWishList(title);
        return new ResponseEntity<>(wishList, HttpStatus.CREATED);
    }

    // Update a Wish List
    @PutMapping("/{id}")
    public ResponseEntity<WishList> updateWishList(@PathVariable int id, @RequestBody String newTitle) {
        WishList updatedWishList = wishesService.updateWishList(id, newTitle);
        if (updatedWishList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedWishList);
    }

    // Delete a Wish List
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishList(@PathVariable int id) {
        wishesService.deleteWishList(id);
        return ResponseEntity.noContent().build();
    }

    // Get all Wishes in a specific Wish List
    @GetMapping("/{wishListId}/wishes")
    public ResponseEntity<List<Wish>> getAllWishes(@PathVariable int wishListId) {
        List<Wish> wishes = wishesService.getAllWishes(wishListId);
        return ResponseEntity.ok(wishes);
    }

    // Create a Wish in a specific Wish List
    @PostMapping("/{wishListId}/wishes")
    public ResponseEntity<Wish> createWish(@PathVariable int wishListId, @RequestBody Wish newWish) {
        Wish wish = wishesService.createWish(wishListId, newWish.getTitle(), newWish.isCompleted());
        return new ResponseEntity<>(wish, HttpStatus.CREATED);
    }

    // Update a Wish in a specific Wish List
    @PutMapping("{wishListId}/wishes/{wishId}")
    public ResponseEntity<Wish> updateWish(@PathVariable int wishListId, @PathVariable int wishId,
                                           @RequestBody Wish updatedWish) {
        try {
            Wish wish = wishesService.updateWish(wishListId, wishId, updatedWish.getTitle(), updatedWish.isCompleted());
            return ResponseEntity.ok(wish);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Mark a Wish as Completed
    @PatchMapping("/{wishListId}/wishes/{wishId}/complete")
    public ResponseEntity<Wish> markWishCompleted(@PathVariable int wishListId, @PathVariable int wishId,
                                                  @RequestParam boolean isCompleted) {
        try {
            Wish wish = wishesService.markWishCompleted(wishListId, wishId, isCompleted);
            return ResponseEntity.ok(wish);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Wish from a specific Wish List
    @DeleteMapping("/{wishListId}/wishes/{wishId}")
    public ResponseEntity<Void> deleteWish(@PathVariable int wishListId, @PathVariable int wishId) {
        try {
            wishesService.deleteWish(wishListId, wishId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
