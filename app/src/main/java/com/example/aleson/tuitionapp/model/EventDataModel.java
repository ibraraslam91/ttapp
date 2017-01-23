package com.example.aleson.tuitionapp.model;

import com.google.firebase.database.Exclude;

/**
 * Created by Aleson on 1/23/2017.
 */

public class EventDataModel {
    @Exclude
    String eventID;
    String eventTitel;
    String startTime;
    String eventUrl;


    public EventDataModel() {
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventTitel() {
        return eventTitel;
    }

    public void setEventTitel(String eventTitel) {
        this.eventTitel = eventTitel;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }
}
