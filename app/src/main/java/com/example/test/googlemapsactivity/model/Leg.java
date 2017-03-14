package com.example.test.googlemapsactivity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Leg {

    @SerializedName("distance")
    @Expose
    private Distance distance;
    @SerializedName("duration")
    @Expose
    private Duration duration;


    @SerializedName("start_address")
    @Expose
    private String startAddress;
    @SerializedName("end_address")
    @Expose
    private String endAddress;
    @SerializedName("steps")
    @Expose
    private List<Step> steps = new ArrayList<>();




    /**
     *
     * @return
     * The distance
     */
    public Distance getDistance() {
        return distance;
    }

    /**
     *
     * @param distance
     * The distance
     */
    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    /**
     *
     * @return
     * The duration
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     *
     * @param duration
     * The duration
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }
    public List<Step> getSteps() {
        return steps;
    }

    public void setStep(List<Step> steps) {
        this.steps = steps;
    }


}
