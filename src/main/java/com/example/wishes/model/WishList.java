package com.example.wishes.model;



import java.util.Map;


public class WishList {
    private int id;
    private String title;
    private Map<Integer, Wish> wishes;

    public WishList(int id, String title, Map<Integer, Wish> wishes) {
        this.id = id;
        this.title = title;
        this.wishes = wishes;
    }



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

    public Map<Integer, Wish> getWishes() {
        return wishes;
    }

    public void setWishes(Map<Integer, Wish> wishes) {
        this.wishes = wishes;
    }
}
