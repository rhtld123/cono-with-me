package com.go.conowithme.api.recruitment.domain.entity;

public enum Genre {
    BALLAD("발라드"),
    HIPHOP("힙합"),
    MUSICAL("뮤지컬 넘버"),
    ROCK("락");

    private String description;

    Genre(String description) {
        this.description = description;
    }
}
