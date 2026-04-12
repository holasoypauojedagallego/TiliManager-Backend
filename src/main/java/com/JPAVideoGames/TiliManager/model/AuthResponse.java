package com.JPAVideoGames.TiliManager.model;

public record AuthResponse(String token, String type, String email, String username) {}
