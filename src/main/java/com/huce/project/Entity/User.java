package com.huce.project.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long userID;
    
    @Column(name = "username",length =50, nullable=false, unique = true)
    private String username;
    
    @Column(name = "pass_word", length = 50, nullable= false)
    private String password;
    
    @Column(name = "email", length = 100, nullable=false)
    private String email;
   
    public User() {}
    

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}

