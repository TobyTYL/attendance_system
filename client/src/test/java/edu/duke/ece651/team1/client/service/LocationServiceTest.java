package edu.duke.ece651.team1.client.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import edu.duke.ece651.team1.client.model.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @InjectMocks
    private LocationService locationService;

    @Mock
    private UserSession userSession;

    @Mock
    private GeoLocationUtil geoLocationUtil;

    @Test
    void testValidateLocation() {
        double studentLatitude = 35.0;
        double studentLongitude = -78.0;
        double professorLatitude = 35.001; // Very close to student's latitude
        double professorLongitude = -78.001; // Very close to student's longitude
        double threshold = 500; // 500 meters threshold
        UserSession.getInstance().setThreshold(threshold);
        UserSession.getInstance().setProfessorLongitude(professorLongitude);
        UserSession.getInstance().setProfesssorLatitude(professorLatitude);
        assertTrue(GeoLocationUtil.areLocationsClose(new Location(professorLatitude, professorLongitude),
                new Location(studentLatitude, studentLongitude), threshold));
        boolean isValid = locationService.validateLocation(studentLatitude, studentLongitude);
        assertTrue(isValid);

    }
}
