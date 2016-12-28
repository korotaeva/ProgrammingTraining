package ru.innopolis.course3.Pojo;

import ru.innopolis.course3.dao.Identified;

/**
 * Created by korot on 23.12.2016.
 * Объект тема
 */
public class Subject implements Identified<Integer> {
    public Subject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String name;

    public Subject(String name, String description, Integer id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public Subject(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;
}
