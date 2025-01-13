package com.example.wishes.controller;

import com.example.wishes.dto.CreateWishDTO;
import com.example.wishes.dto.CreateWishListDTO;
import com.example.wishes.dto.WishListResponseDTO;
import com.example.wishes.dto.WishResponseDTO;
import com.example.wishes.mapper.WishMapper;
import com.example.wishes.model.Wish;
import com.example.wishes.model.WishList;
import com.example.wishes.service.WishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wishlists")
public class WishesController {

    @Autowired
    private WishesService wishesService;

    @Autowired
    private WishMapper wishMapper;

    // Get all Wish Lists
    @GetMapping
    public ResponseEntity<List<WishListResponseDTO>> getAllWishLists() {
        List<WishList> wishLists = wishesService.getAllWishLists();
        List<WishListResponseDTO> wishListResponseDTOs = wishLists.stream()
                .map(wishMapper::toWishListDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(wishListResponseDTOs);
    }

    // Get a specific Wish List by ID
    @GetMapping("/{id}")
    public ResponseEntity<WishListResponseDTO> getWishListById(@PathVariable int id) {
        WishList wishList = wishesService.getWishListByID(id);
        return ResponseEntity.ok(wishMapper.toWishListDTO(wishList));
    }

    // Create a new Wish List
    @PostMapping
    public ResponseEntity<WishListResponseDTO> createWishList(@RequestBody CreateWishListDTO createWishListDTO) {
        WishList wishList = wishesService.createWishList(createWishListDTO.getTitle());
        return new ResponseEntity<>(wishMapper.toWishListDTO(wishList), HttpStatus.CREATED);
    }

    // Update a Wish List
    @PutMapping("/{id}")
    public ResponseEntity<WishListResponseDTO> updateWishList(@PathVariable int id, @RequestBody String newTitle) {
        WishList updatedWishList = wishesService.updateWishList(id, newTitle);
        return ResponseEntity.ok(wishMapper.toWishListDTO(updatedWishList));
    }

    // Delete a Wish List
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishList(@PathVariable int id) {
        wishesService.deleteWishList(id);
        return ResponseEntity.noContent().build();
    }


    // Get all Wishes in a specific Wish List
    @GetMapping("/{wishListId}/wishes")
    public ResponseEntity<List<WishResponseDTO>> getAllWishes(@PathVariable int wishListId) {
        List<Wish> wishes = wishesService.getAllWishes(wishListId);
        List<WishResponseDTO> wishResponseDTOs = wishes.stream()
                .map(wishMapper::toWishDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(wishResponseDTOs);
    }

    // Create a Wish in a specific Wish List
    @PostMapping("/{wishListId}/wishes")
    public ResponseEntity<WishResponseDTO> createWish(@PathVariable int wishListId, @RequestBody CreateWishDTO newWishDTO) {
        Wish wish = wishesService.createWish(wishListId, newWishDTO.getTitle(), false);
        return new ResponseEntity<>(wishMapper.toWishDTO(wish), HttpStatus.CREATED);
    }

    // Update a Wish in a specific Wish List
    @PutMapping("{wishListId}/wishes/{wishId}")
    public ResponseEntity<WishResponseDTO> updateWish(@PathVariable int wishListId, @PathVariable int wishId,
                                                      @RequestBody CreateWishDTO updatedWish) {

        Wish wish = wishesService.updateWish(wishListId, wishId, updatedWish.getTitle(), false);
        return ResponseEntity.ok(wishMapper.toWishDTO(wish));

    }

    // Mark a Wish as Completed
    @PatchMapping("/{wishListId}/wishes/{wishId}/complete")
    public ResponseEntity<WishResponseDTO> markWishCompleted(@PathVariable int wishListId, @PathVariable int wishId) {

        Wish wish = wishesService.markWishCompleted(wishListId, wishId, true);
        return ResponseEntity.ok(wishMapper.toWishDTO(wish));

    }

    // Delete a Wish from a specific Wish List
    @DeleteMapping("/{wishListId}/wishes/{wishId}")
    public ResponseEntity<Void> deleteWish(@PathVariable int wishListId, @PathVariable int wishId) {

            wishesService.deleteWish(wishListId, wishId);
            return ResponseEntity.noContent().build();
    }
}
