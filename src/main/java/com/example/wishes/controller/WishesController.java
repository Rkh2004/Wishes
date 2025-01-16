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

import java.security.Principal;
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
    public ResponseEntity<List<WishListResponseDTO>> getAllWishLists(Principal principal) {
        String username = principal.getName();
        List<WishList> wishLists = wishesService.getAllWishLists(username);
        List<WishListResponseDTO> wishListResponseDTOs = wishLists.stream()
                .map(wishMapper::toWishListDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(wishListResponseDTOs);
    }

    // Get a specific Wish List by ID
    @GetMapping("/{id}")
    public ResponseEntity<WishListResponseDTO> getWishListById(Principal principal, @PathVariable int id) {
        String username = principal.getName();
        WishList wishList = wishesService.getWishListByID(username, id);
        return ResponseEntity.ok(wishMapper.toWishListDTO(wishList));
    }

    // Create a new Wish List
    @PostMapping
    public ResponseEntity<WishListResponseDTO> createWishList(Principal principal, @RequestBody CreateWishListDTO createWishListDTO) {
        String username = principal.getName();
        WishList wishList = wishesService.createWishList(username, createWishListDTO.getTitle());
        return new ResponseEntity<>(wishMapper.toWishListDTO(wishList), HttpStatus.CREATED);
    }

    // Update a Wish List
    @PutMapping("/{id}")
    public ResponseEntity<WishListResponseDTO> updateWishList(Principal principal, @PathVariable int id, @RequestBody String newTitle) {
        String username = principal.getName();
        WishList updatedWishList = wishesService.updateWishList(username, id, newTitle);
        return ResponseEntity.ok(wishMapper.toWishListDTO(updatedWishList));
    }

    // Delete a Wish List
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishList(Principal principal, @PathVariable int id) {
        String username = principal.getName();
        wishesService.deleteWishList(username, id);
        return ResponseEntity.noContent().build();
    }


    // Get all Wishes in a specific Wish List
    @GetMapping("/{wishListId}/wishes")
    public ResponseEntity<List<WishResponseDTO>> getAllWishes(Principal principal, @PathVariable int wishListId) {
        String username = principal.getName();
        List<Wish> wishes = wishesService.getAllWishes(username, wishListId);
        List<WishResponseDTO> wishResponseDTOs = wishes.stream()
                .map(wishMapper::toWishDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(wishResponseDTOs);
    }

    // Create a Wish in a specific Wish List
    @PostMapping("/{wishListId}/wishes")
    public ResponseEntity<WishResponseDTO> createWish(Principal principal, @PathVariable int wishListId, @RequestBody CreateWishDTO newWishDTO) {
        String username = principal.getName();
        Wish wish = wishesService.createWish(username, wishListId, newWishDTO.getTitle(), false);
        return new ResponseEntity<>(wishMapper.toWishDTO(wish), HttpStatus.CREATED);
    }

    // Update a Wish in a specific Wish List
    @PutMapping("{wishListId}/wishes/{wishId}")
    public ResponseEntity<WishResponseDTO> updateWish(Principal principal, @PathVariable int wishListId, @PathVariable int wishId,
                                                      @RequestBody CreateWishDTO updatedWish) {
        String username = principal.getName();
        Wish wish = wishesService.updateWish(username, wishListId, wishId, updatedWish.getTitle(), false);
        return ResponseEntity.ok(wishMapper.toWishDTO(wish));

    }

    // Mark a Wish as Completed
    @PatchMapping("/{wishListId}/wishes/{wishId}/complete")
    public ResponseEntity<WishResponseDTO> markWishCompleted(Principal principal, @PathVariable int wishListId, @PathVariable int wishId) {

        String username = principal.getName();
        Wish wish = wishesService.markWishCompleted(username, wishListId, wishId, true);
        return ResponseEntity.ok(wishMapper.toWishDTO(wish));

    }

    // Delete a Wish from a specific Wish List
    @DeleteMapping("/{wishListId}/wishes/{wishId}")
    public ResponseEntity<Void> deleteWish(Principal principal, @PathVariable int wishListId, @PathVariable int wishId) {

        String username = principal.getName();
        wishesService.deleteWish(username, wishListId, wishId);
        return ResponseEntity.noContent().build();
    }
}
