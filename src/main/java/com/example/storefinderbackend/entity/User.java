package com.example.storefinderbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;
    @Column(name = "email_id", nullable = false, unique = true)
    private String email;

    private String password;
    @Column(name = "role")
    private String role;

    @ElementCollection
    @Column(name="favStores")
    private List<Long> favStores=new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    public List<Long> getFavStores(){
//        return favStores;
//    }
//
//    public void addFavStore(Long id){
//        this.favStores.add(id);
//    }
//
//    public void removeFavStore(Long id) {
//        this.favStores.remove(id);
//    }

    public List<Long> getFavoriteStores() {
        return favStores;
    }

    public void setFavoriteStores(List<Long> favStores) {
        this.favStores = favStores;
    }

    public void addFavoriteStore(Long storeId) {
        if (!favStores.contains(storeId)) {
            favStores.add(storeId);
        }
    }

    public void removeFavoriteStore(Long storeId) {
        favStores.remove(storeId);
    }
}
