package com.example.aleson.tuitionapp.model;

import com.google.firebase.database.Exclude;

/**
 * Created by Aleson on 1/23/2017.
 */

public class SubjectDataModel {
    @Exclude
    private String subjectID;
    private String subjectName;
    private String teacher;
    private String lectureID;
    private String timeing;
    private String filesURL;

    public SubjectDataModel() {
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getLectureID() {
        return lectureID;
    }

    public void setLectureID(String lectureID) {
        this.lectureID = lectureID;
    }

    public String getTimeing() {
        return timeing;
    }

    public void setTimeing(String timeing) {
        this.timeing = timeing;
    }

    public String getFilesURL() {
        return filesURL;
    }

    public void setFilesURL(String filesURL) {
        this.filesURL = filesURL;
    }
}
