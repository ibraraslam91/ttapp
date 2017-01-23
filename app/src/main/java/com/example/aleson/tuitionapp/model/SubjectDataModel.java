package com.example.aleson.tuitionapp.model;

import com.google.firebase.database.Exclude;

/**
 * Created by Aleson on 1/23/2017.
 */

public class SubjectDataModel {
    @Exclude
    String subjectID;
    String teacher;
    String eventNodeID;

    public SubjectDataModel() {
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getEventNodeID() {
        return eventNodeID;
    }

    public void setEventNodeID(String eventNodeID) {
        this.eventNodeID = eventNodeID;
    }
}
