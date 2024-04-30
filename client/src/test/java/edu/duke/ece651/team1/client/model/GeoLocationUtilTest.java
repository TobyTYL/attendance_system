package edu.duke.ece651.team1.client.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class GeoLocationUtilTest {

    @Test
    void testCalculateDistance() {
        Location paris = new Location(48.8566, 2.3522);
        Location london = new Location(51.5074, -0.1278);
        double distance = GeoLocationUtil.calculateDistance(paris, london);
        assertTrue(Math.abs(distance - 344000) < 10000, "Distance between Paris and London should be about 344 kilometers.");
    }

    @Test
    void testAreLocationsClose() {
        Location pointA = new Location(48.8567, 2.3522); // Paris adjusted
        Location pointB = new Location(48.8560, 2.3512); // Very close to pointA
        assertTrue(GeoLocationUtil.areLocationsClose(pointA, pointB, 1000), "Points should be considered close.");
        assertFalse(GeoLocationUtil.areLocationsClose(pointA, pointB, 10), "Points should not be considered close with a threshold of 10 meters.");
    }
}
