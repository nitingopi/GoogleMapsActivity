package com.example.test.googlemapsactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by test on 20/2/17.
 */
public class Step implements Serializable {

    @SerializedName("distance")
    @Expose
    private Distance distance;

    @SerializedName("duration")
    @Expose
    private Duration duration;

    @SerializedName("html_instructions")
    @Expose
    private String path;

    @SerializedName("polyline")
    @Expose
    private Polyline polyline;

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }




}
