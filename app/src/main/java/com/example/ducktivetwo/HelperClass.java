package com.example.ducktivetwo;
public class HelperClass {
    String id;
    String username;
    String email;
    String password;
    String phone;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public HelperClass(String id, String name, String email, String username, String password) {
        this.username = name;
        this.email = email;
        this.phone = username;
        this.password = password;
        this.id = id;
    }
    public HelperClass() {
    }
}
