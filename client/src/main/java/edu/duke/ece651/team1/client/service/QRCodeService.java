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
/**
 * Service for generating QR codes with optional expiring URLs.
 * This service utilizes external libraries to create QR codes for URLs, which can include security tokens with an expiration mechanism.
 */
@Service
public class QRCodeService {
    @Autowired
    TokenService tokenService;
    /**
     * Generates a URL with an expiring token appended as a query parameter. The token is meant to enhance security by limiting the URL's validity period.
     *
     * @param baseUrl the base URL to which the token will be appended
     * @param validityInSeconds the duration in seconds for which the token remains valid
     * @return a string representing the full URL with the expiring token
     */
    public String generateExpiringQRCodeURLwithLocation(String baseUrl, long validityInSeconds) {
        String token = tokenService.createToken();
        return baseUrl + "?token=" + token;
    }
     /**
     * Generates a QR code image for a given URL and encodes it as a Base64 string. Optionally includes a mechanism to append an expiring token to the URL.
     *
     * @param url the URL to encode in the QR code
     * @param urlWithExpire a boolean indicating whether to append an expiring token to the URL
     * @return a Base64-encoded string representing the QR code image
     * @throws WriterException if there is an error generating the QR code
     * @throws IOException if there is an error writing the QR code to the output stream
     */
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

    

}
