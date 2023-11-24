package com.example.merchantmanagement.Leaderboard;



public class LeaderBoardObject {
    private String SalespersonName, timestamp;

    public LeaderBoardObject(){

    }
    public LeaderBoardObject(String salespersonName, String timestamp) {
        this.SalespersonName = salespersonName;
        this.timestamp = timestamp;
    }

    public void setSalespersonName(String salespersonName) {
        SalespersonName = salespersonName;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSalespersonName() {
        return SalespersonName;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
