package com.mirza.simplysocial.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Column(nullable = false)
    private String realName;

    @Column(unique = true)
    private String sessionToken;

    private LocalDateTime sessionTokenExpiry;

    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public User(){}

    public User(String realName, String username, String password) {
        this.realName = realName;
        this.username = username;
        this.password = password;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSessionToken() {return sessionToken;}
    public void setSessionToken(String sessionToken) {this.sessionToken = sessionToken;}
    public LocalDateTime getSessionTokenExpiry() {return sessionTokenExpiry;}
    public void setSessionTokenExpiry(LocalDateTime sessionTokenExpiry) {this.sessionTokenExpiry = sessionTokenExpiry;}
}
