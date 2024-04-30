package edu.duke.ece651.team1.client.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void testLocationConstructorAndGetters() {
        double expectedLatitude = 48.8566;  
        double expectedLongitude = 2.3522; 
        Location location = new Location(expectedLatitude, expectedLongitude);
        assertEquals(expectedLatitude, location.getLatitude(), "The latitude should match the expected value.");
        assertEquals(expectedLongitude, location.getLongitude(), "The longitude should match the expected value.");
    }

    @Test
    void testNegativeCoordinates() {
        double expectedLatitude = -34.6037;  
        double expectedLongitude = -58.3816; 
        Location location = new Location(expectedLatitude, expectedLongitude);
        assertEquals(expectedLatitude, location.getLatitude(), "The latitude should correctly handle negative values.");
        assertEquals(expectedLongitude, location.getLongitude(), "The longitude should correctly handle negative values.");
    }

}
