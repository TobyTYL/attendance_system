package edu.duke.ece651.team1.client.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;
@ContextConfiguration(classes = {TokenService.class})
@ExtendWith(SpringExtension.class)

public class TokenServiceTest {

    @Autowired
    TokenService tokenService;

    @Test
    public void testTokenCreationAndImmediateValidation() {
        String token = tokenService.createToken();
        assertTrue(tokenService.validateToken(token));
    }

    // @Test
    // @Timeout(value = 40, unit = TimeUnit.SECONDS)
    // public void testTokenExpiresAfter30Seconds() throws InterruptedException {
    //     String token = tokenService.createToken();
    //     assertTrue(tokenService.validateToken(token));  
    //     TimeUnit.SECONDS.sleep(31);
    //     assertFalse(tokenService.validateToken(token));  
    // }

    // @Test
    // @Timeout(value = 1, unit = TimeUnit.MINUTES)
    // public void testCleanupTokensRemovesExpiredTokens() throws InterruptedException {
    //     String token = tokenService.createToken();
    //     TimeUnit.SECONDS.sleep(31);
    //     tokenService.cleanupTokens();
    //     assertFalse(tokenService.validateToken(token));  
    //     assertEquals(0, tokenService.getTokenStoreSize());
    // }
}
