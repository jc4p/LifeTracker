package com.kasra.lifetracker.models;

import java.util.ArrayList;
import java.util.Date;

public class Task {
    public long _id;
    public String name;
    public int color;
    private Date lastEventAt;
    private double[] geoFenceCoordinates;

    public ArrayList<TaskEvent> events;

    public Task(long _id, String name, int color, double[] geoFenceCoordinates, Date lastEventAt) {
        this._id = _id;
        this.name = name;
        this.color = color;
        this.geoFenceCoordinates = geoFenceCoordinates;
        this.lastEventAt = lastEventAt;
        this.events = new ArrayList<>();
    }
}
