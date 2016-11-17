package com.example.nabil.moviestest;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nabil on 2/18/2016.
 */
public class Movie implements Serializable {

    private String name;
    private String overview;
    private String imageURL;
    private String trailerURL;
    private String releaseDate;
    private char belongsTo;
    private float votes;

    public Movie(){
        this.name = "";
        this.imageURL = "";
        this.overview = "";
        this.trailerURL = "";
    }

    public Movie(String name, String overview, String imageURL, String trailerURL, float votes){
        this.name = name;
        this.imageURL = imageURL;
        this.overview = overview;
        this.trailerURL = trailerURL;
        this.votes = votes;
    }

    public String getOverview() {
        return overview;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTrailerURL(){
        return trailerURL;
    }

    public float getVotes() {
        return votes;
    }

    public void setVotes(float votes) {
        this.votes = votes;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setOverview(String overview){
        this.overview = overview;
    }

    public void setImageURL(String imageURL){
        this.imageURL = imageURL;
    }

    public void setTrailerURL(String trailerURL){
        this.trailerURL = trailerURL;
    }

    public String getReleaseDate() { return releaseDate; }

    public void setReleaseDate(String date) { this.releaseDate = date; }

    public void setBelongsTo(char c) { this.belongsTo = c; }

    public char getBelongsTo() { return this.belongsTo; }

    @Override
    public String toString(){
        String str = "";

        str = "Movie : " + this.name +
                "\nOverview: " + this.overview +
                "\nTrailer: " + "http://www.youtube.com/watch?v=" + trailerURL;
        return str;
    }

}
