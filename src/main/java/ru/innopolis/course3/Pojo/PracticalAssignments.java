package ru.innopolis.course3.Pojo;

import ru.innopolis.course3.dao.Identified;

/**
 * Created by korot on 24.12.2016.
 * Объект практические задания
 */
public class PracticalAssignments implements Identified<Integer> {
    public PracticalAssignments() {
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
    private String description;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public PracticalAssignments(String name, String description, Subject subject, Integer id) {
        this.name = name;
        this.description = description;
        this.subject = subject;
        this.id = id;
    }

    public PracticalAssignments(String name, String description, Subject subject) {
        this.name = name;
        this.description = description;
        this.subject = subject;
    }

    private Subject subject;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;
}
