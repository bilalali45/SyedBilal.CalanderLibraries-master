package com.syedbilalali.customcalanderview;


public class Hourslots {

    private String hour_slot_id = "";

    private String from_time = "";

    private String to_time = "";

    public Double getRates() {
        return rates;
    }

    public void setRates(Double rates) {
        this.rates = rates;
    }

    private Double rates = 0.0;
    private String self_from_time = "";

    public String getSelf_from_time() {
        return self_from_time;
    }

    public void setSelf_from_time(String self_from_time) {
        this.self_from_time = self_from_time;
    }

    public String getSelf_to_time() {
        return self_to_time;
    }

    public void setSelf_to_time(String self_to_time) {
        this.self_to_time = self_to_time;
    }

    public String getSelf_hour_slot_id() {
        return self_hour_slot_id;
    }

    public void setSelf_hour_slot_id(String self_hour_slot_id) {
        this.self_hour_slot_id = self_hour_slot_id;
    }

    private String self_to_time = "";

    private String self_hour_slot_id = "";
    public Hourslots() {
    }

    public String getHour_slot_id() {
        return hour_slot_id;
    }

    public void setHour_slot_id(String hour_slot_id) {
        this.hour_slot_id = hour_slot_id;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }
}