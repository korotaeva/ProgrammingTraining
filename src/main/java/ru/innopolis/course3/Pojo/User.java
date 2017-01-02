package ru.innopolis.course3.Pojo;

import ru.innopolis.course3.dao.Identified;

/**
 * Created by korot on 24.12.2016.
 * Объект пользователь
 */
public class User implements Identified<Integer> {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    private int id;
    private String name;
    private String password;
    private String email;
    private String phone;


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User(){

    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(Integer id, String name, String password, String email, String phone, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public User(String name, String password, String email, String phone, Role role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    private Role role;
}
