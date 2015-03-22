package com.kasra.lifetracker.models;

import java.util.ArrayList;

public class Task {
    public int _id;
    public String name;
    public int color;
    private double[] geoFenceCoordinates;

    public ArrayList<TaskEvent> events;

    public Task(String name, int color, double[] geoFenceCoordinates) {
        this.name = name;
        this.color = color;
        this.geoFenceCoordinates = geoFenceCoordinates;
        this.events = new ArrayList<>();
    }
}
