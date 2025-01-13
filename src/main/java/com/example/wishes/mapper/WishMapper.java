package com.example.wishes.mapper;

import com.example.wishes.dto.WishListResponseDTO;
import com.example.wishes.dto.WishResponseDTO;
import com.example.wishes.model.Wish;
import com.example.wishes.model.WishList;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class WishMapper {

    public WishResponseDTO toWishDTO(Wish wish){
        WishResponseDTO wishResponseDTO = new WishResponseDTO();
        wishResponseDTO.setId(wish.getId());
        wishResponseDTO.setTitle(wish.getTitle());
        wishResponseDTO.setCompleted(wish.isCompleted());
        return wishResponseDTO;
    }

    public WishListResponseDTO toWishListDTO(WishList wishList){

        WishListResponseDTO wishListResponseDTO = new WishListResponseDTO();
        wishListResponseDTO.setId(wishList.getId());
        wishListResponseDTO.setTitle(wishList.getTitle());
        wishListResponseDTO.setWishes(wishList.getWishes().stream()
                .map(this::toWishDTO)
                .collect(Collectors.toList()));
        return wishListResponseDTO;
    }

}
