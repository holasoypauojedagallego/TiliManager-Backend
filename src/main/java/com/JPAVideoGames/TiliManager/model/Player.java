package com.JPAVideoGames.TiliManager.model;

public class Player {

    private long id;
    private String name;
    private int rating;
    private int attack;
    private int defense;

    public Player(long id, String name, int rating, int attack, int defense) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.attack = attack;
        this.defense = defense;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
