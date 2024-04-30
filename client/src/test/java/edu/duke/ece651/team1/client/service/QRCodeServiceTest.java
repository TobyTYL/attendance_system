package edu.duke.ece651.team1.client.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.google.zxing.WriterException;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
public class QRCodeServiceTest {

    @InjectMocks
    private QRCodeService qrCodeService;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        when(tokenService.createToken()).thenReturn("dummy_token");
    }

    @Test
    public void testGenerateQRCodeImageWithoutExpiring() throws WriterException, IOException {
        String baseUrl = "http://example.com";
        String qrCodeImage = qrCodeService.generateQRCodeImage(baseUrl, false);
        assertNotNull(qrCodeImage);
    }

    @Test
    public void testGenerateQRCodeImageWithExpiring() throws WriterException, IOException {
        String baseUrl = "http://example.com";
        String qrCodeImage = qrCodeService.generateQRCodeImage(baseUrl, true);
        assertNotNull(qrCodeImage);
        verify(tokenService).createToken(); // Ensure token creation is invoked
    }

    @Test
    public void testGenerateExpiringQRCodeURLwithLocation() {
        String baseUrl = "http://example.com";
        long validityInSeconds = 30; 
        String urlWithToken = qrCodeService.generateExpiringQRCodeURLwithLocation(baseUrl, validityInSeconds);
        assertTrue(urlWithToken.contains("token=dummy_token")); // Verify that the URL contains the token
    }
}

