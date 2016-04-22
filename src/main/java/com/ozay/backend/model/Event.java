package com.ozay.backend.model;

import org.joda.time.DateTime;

/**
 * Created by naofumiezaki on 10/31/15.
 */
public class Event {
    private Long id;
    private String name;
    private DateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return "Event{" +
            "id='" + id + '\'' +
            "name='" + name + '\'' +
            "}";
    }
}