package com.example.sharemeal.activity.models;

public class User {
    public String name, email, tokenID;

    public User(){
    }

    public User(String name, String email, String tokenID) {
        this.name = name;
        this.email = email;
        this.tokenID = tokenID;
    }

    public String getName() {
        return name;
    }



    public String getEmail() {
        return email;
    }

    public String getTokenID() {
        return tokenID;
    }
}
