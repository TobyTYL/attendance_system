package edu.duke.ece651.team1.client.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import edu.duke.ece651.team1.client.model.GeoLocationUtil;
import edu.duke.ece651.team1.client.model.Location;
import edu.duke.ece651.team1.client.model.UserSession;

import java.time.Instant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

@Service
public class QRCodeService {
    @Autowired
    TokenService tokenService;

    public String generateExpiringQRCodeURLwithLocation(String baseUrl, long validityInSeconds) {
        String token = tokenService.createToken();
        return baseUrl + "?token=" + token;
    }

    public String generateQRCodeImage(String url,boolean urlWithExpire) throws WriterException, IOException {
        MultiFormatWriter barcodeWriter = new MultiFormatWriter();
        if(urlWithExpire){
          url = generateExpiringQRCodeURLwithLocation(url, 30);
        }
        BitMatrix bitMatrix = barcodeWriter.encode(url, BarcodeFormat.QR_CODE, 200, 200);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return Base64Utils.encodeToString(pngData);
    }

    public boolean validateLocation(double s_Latitude, double s_Longtitude) {
        double threshold = UserSession.getInstance().getThreshold();
        double professorLongitude = UserSession.getInstance().getProfessorLongitude();
        double professsorLatitude = UserSession.getInstance().getProfesssorLatitude();

        return GeoLocationUtil.areLocationsClose(new Location(professsorLatitude, professorLongitude),new Location(s_Latitude, s_Longtitude),
                threshold);
    }

}
