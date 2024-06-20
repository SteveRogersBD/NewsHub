package com.example.newshub.Models;

public class NewsPaper {
    int imageI;
    String imageS,name,link;


    public NewsPaper(String name, String link, int image)
    {
        this.name = name;
        this.imageI = image;
        this.link = link;
    }
    public NewsPaper(int imageI, String name, String link) {
        this.imageI = imageI;
        this.name = name;
        this.link = link;
    }

    public NewsPaper(int imageI, String imageS, String name, String link) {
        this.imageI = imageI;
        this.imageS = imageS;
        this.name = name;
        this.link = link;
    }

    public int getImageI() {
        return imageI;
    }

    public void setImageI(int imageI) {
        this.imageI = imageI;
    }

    public String getImageS() {
        return imageS;
    }

    public void setImageS(String imageS) {
        this.imageS = imageS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
