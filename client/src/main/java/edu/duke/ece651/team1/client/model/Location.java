package edu.duke.ece651.team1.client.model;

public class Location {
    double latitude;
    double longitude;
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }
 
    public double getLongitude() {
        return longitude;
    }
  
}
