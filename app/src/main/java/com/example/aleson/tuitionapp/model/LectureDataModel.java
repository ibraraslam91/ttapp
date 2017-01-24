package com.example.aleson.tuitionapp.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.Map;

/**
 * Created by Aleson on 1/24/2017.
 */

public class LectureDataModel {

    @Exclude
    private String lectureID;
    private String lectureTitel;
    private String lectureDec;
    private long lectureTime;
    private String lectureUrl;
    private String quizID;



    public String getLectureID() {
        return lectureID;
    }

    public void setLectureID(String lectureID) {
        this.lectureID = lectureID;
    }

    public String getLectureTitel() {
        return lectureTitel;
    }

    public void setLectureTitel(String lectureTitel) {
        this.lectureTitel = lectureTitel;
    }

    public String getLectureDec() {
        return lectureDec;
    }

    public void setLectureDec(String lectureDec) {
        this.lectureDec = lectureDec;
    }

    public long getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(long lectureTime) {
        this.lectureTime = lectureTime;
    }

    public String getLectureUrl() {
        return lectureUrl;
    }

    public void setLectureUrl(String lectureUrl) {
        this.lectureUrl = lectureUrl;
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }
}
