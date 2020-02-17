package com.example.waholy;

public class User {
    private String email;
    private String password;
    private String fullname;
    private String phone;
    public User() {

    }
    public User(String email, String password, String fullname, String phone) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhone() {
        return phone;
    }
}
