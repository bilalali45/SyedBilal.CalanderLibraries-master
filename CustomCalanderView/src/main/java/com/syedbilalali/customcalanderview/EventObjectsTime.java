package com.syedbilalali.customcalanderview;

import java.util.ArrayList;
import java.util.Date;

public class EventObjectsTime {
    private String dayname;
    private String Rates;

    public String getPeercentage() {
        return peercentage;
    }

    public void setPeercentage(String peercentage) {
        this.peercentage = peercentage;
    }

    public String getHoursRate() {
        return hoursRate;
    }

    public void setHoursRate(String hoursRate) {
        this.hoursRate = hoursRate;
    }

    private String peercentage;
    private String hoursRate;

    public ArrayList<Hourslots> getHourslots() {
        return hourslots;
    }

    public void setHourslots(ArrayList<Hourslots> hourslots) {
        this.hourslots = hourslots;
    }

    private ArrayList<Hourslots> hourslots = new ArrayList<>();


    public int getDescount() {
        return descount;
    }

    public void setDescount(int descount) {
        this.descount = descount;
    }

    private int descount;
    private int id = 0;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getDayname() {
        return dayname;
    }

    public void setDayname(String dayname) {
        this.dayname = dayname;
    }

    public String getRates() {
        return Rates;
    }

    public void setRates(String rates) {
        Rates = rates;
    }


}
