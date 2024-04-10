// package edu.duke.ece651.team1.client.model.utils;

// import static org.junit.jupiter.api.Assertions.*;


// import org.junit.jupiter.api.Test;


// class PasswordUtilsTest {

//     @Test
//     public void testPwdGenConsistence() {
//         String salt = PasswordUtils.generateSalt();
//         String pwd = "test123";
//         String firstHash = PasswordUtils.hashPassword(pwd, salt);
//         String secondHash = PasswordUtils.hashPassword(pwd, salt);
//         assertEquals(firstHash, secondHash);
//     }

// }