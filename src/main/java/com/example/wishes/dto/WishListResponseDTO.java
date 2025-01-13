package com.example.wishes.dto;

import com.example.wishes.model.Wish;

import java.util.List;

public class WishListResponseDTO {
    private int id;
    private String title;
    private List<WishResponseDTO> wishes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<WishResponseDTO> getWishes() {
        return wishes;
    }

    public void setWishes(List<WishResponseDTO> wishes) {
        this.wishes = wishes;
    }
}

