package edu.duke.ece651.team1.client.model;

public class GeoLocationUtil {

  
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371e3; 
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

   
    public static boolean areLocationsClose(double lat1, double lon1, double lat2, double lon2, double threshold) {
        return calculateDistance(lat1, lon1, lat2, lon2) <= threshold;
    }

    public static void main(String[] args) {
        
        double lat1 =-78.9663637; //professor
        double lon1 = 35.9647835; // 
        double lat2 = 35.964753627810595; // student
        double lon2 =-78.96632619209491; 

        double distance = GeoLocationUtil.calculateDistance(lat1, lon1, lat2, lon2);
        System.out.println("The distance between Student and Professor is: " + distance + " meters");

        // 检查两点是否靠近，这里我们设置阈值为 1000 公里
        double threshold = 10; // 1000 km in meters
        boolean isClose = GeoLocationUtil.areLocationsClose(lat1, lon1, lat2, lon2, threshold);
        System.out.println("Are the two locations within 1000 km of each other? " + isClose);
    }

}
