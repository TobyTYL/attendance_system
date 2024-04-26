package edu.duke.ece651.team1.client.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
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
    public String generateExpiringQRCodeURLwithLocation(String baseUrl, long validityInSeconds,double latitude, double longitude) {
        String token = tokenService.createToken(latitude,longitude);
        return baseUrl + "?token=" + token;
    }

    public String generateQRCodeImage(String url, double latitude, double longitude) throws WriterException, IOException {
        MultiFormatWriter barcodeWriter = new MultiFormatWriter();
        String urlWithExpiry = generateExpiringQRCodeURLwithLocation(url, 30,latitude,longitude);
        BitMatrix bitMatrix = barcodeWriter.encode(urlWithExpiry, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return Base64Utils.encodeToString(pngData);
    }

}
