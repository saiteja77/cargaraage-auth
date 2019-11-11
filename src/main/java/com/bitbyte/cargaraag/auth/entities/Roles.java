package com.bitbyte.cargaraag.auth.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    int id;

    @Column(unique = true)
    private String role;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id"))
    private Set<User> users;

    public Roles(String role) {
        this.role = role;
    }

    public Roles() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        if (users == null) {
            users = new HashSet<>();
        }
        users.add(user);
    }
}
