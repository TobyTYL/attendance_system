package edu.duke.ece651.team1.client.service;

import edu.duke.ece651.team1.client.model.GeoLocationUtil;
import edu.duke.ece651.team1.client.model.Location;
import edu.duke.ece651.team1.client.model.UserSession;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    public boolean validateLocation(double s_Latitude, double s_Longtitude) {
        double threshold = UserSession.getInstance().getThreshold();
        double professorLongitude = UserSession.getInstance().getProfessorLongitude();
        double professsorLatitude = UserSession.getInstance().getProfesssorLatitude();
        return GeoLocationUtil.areLocationsClose(new Location(professsorLatitude, professorLongitude),new Location(s_Latitude, s_Longtitude),
                threshold);
    }
}
