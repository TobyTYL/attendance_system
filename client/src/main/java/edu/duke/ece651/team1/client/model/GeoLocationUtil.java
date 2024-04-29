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

    public static void main(String[] args) {
        
        double lat1 =-78.9663637; //professor
        double lon1 = 35.9647835; // 
        double lat2 = 35.964753627810595; // student
        double lon2 =-78.96632619209491; 
        Location location1 = new Location(lat1, lon1);
        Location location2 = new Location(lat2, lon2);

        double distance = GeoLocationUtil.calculateDistance(location1,location2);
        System.out.println("The distance between Student and Professor is: " + distance + " meters");

        // 检查两点是否靠近，这里我们设置阈值为 1000 公里
        double threshold = 10; // 1000 km in meters
        boolean isClose = GeoLocationUtil.areLocationsClose(location1,location2, threshold);
        System.out.println("Are the two locations within 1000 km of each other? " + isClose);
    }

}
