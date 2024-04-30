package edu.duke.ece651.team1.client.model;

public class GeoLocationUtil {

  
    public static double calculateDistance(Location location1, Location location2) {
        final double R = 6371e3; 
        double lat1 = location1.latitude;
        double lat2 = location2.latitude;
        double lon1 = location1.longitude;
        double lon2 = location2.longitude;
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; 
    }

   
    public static boolean areLocationsClose(Location location1, Location location2, double threshold) {
        System.out.println( calculateDistance(location1,location2));
        return calculateDistance(location1,location2) <= threshold;
    }

    
}
