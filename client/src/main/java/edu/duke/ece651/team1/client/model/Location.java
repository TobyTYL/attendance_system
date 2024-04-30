package edu.duke.ece651.team1.client.model;
/**
 * Represents a geographical location with latitude and longitude coordinates.
 * This class provides methods to access the latitude and longitude values of a specific location.
 */
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
