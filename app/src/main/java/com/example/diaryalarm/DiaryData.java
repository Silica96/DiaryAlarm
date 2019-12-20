package com.example.diaryalarm;

public class DiaryData {
    private int image;
    private String diary_title;
    private String content;

    public DiaryData(int image, String dTitle, String content) {
        this.image = image;
        this.diary_title = dTitle;
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public String getDiary_title() {
        return diary_title;
    }

    public String getContent() {
        return content;
    }
}
