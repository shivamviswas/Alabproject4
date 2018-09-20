package com.example.abhishekbaari.alabproject4.modal;

/**
 * Created by Aws on 11/03/2018.
 */

public class Marks {

    private String stName ;
    private String stId;
    private String stSubject;
    private String examName;
    private String totalmarks;
    private String obtmarks ;
    private String image_url_student;

    public Marks() {
    }
    public String getObtmarks() {
        return obtmarks;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public void setObtmarks(String obtmarks) {
        this.obtmarks = obtmarks;
    }

    public String getStName() {
        return stName;
    }

    public String getStSubject() {
        return stSubject;
    }

    public String getTotalmarks() {
        return totalmarks;
    }

    public String getStId() {
        return stId;
    }

    public String getImage_url_student() {
        return image_url_student;
    }


    public void setStName(String name) {
        this.stName = name;
    }

    public void setStSubject(String description) {
        stSubject = description;
    }
    public void setTotalmarks(String name) {
        this.totalmarks = name;
    }

    public void setStId(String nb_episode) {
        this.stId = nb_episode;
    }

    public void setImage_url_student(String image_url) {
        this.image_url_student = image_url;
    }
}
