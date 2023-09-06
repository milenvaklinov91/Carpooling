package com.telerikacademy.carpooling.models.enums;

public enum TripStatus {
    AWAITING, INPROGRESS, FINISHED;

    @Override
    public String toString() {
        if (this.equals(AWAITING)){
           return  "AWAITING";
        } else if (this.equals(INPROGRESS)) {
            return  "INPROGRESS";
        } else {
            return "FINISHED";
        }
    }
}
