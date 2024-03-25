package edu.duke.ece651.team1.client.model.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// import static org.junit.jupiter.api.Assertions.*;

class EmailUtilsTest {
    @Test
    public void checkEmailTest() {
        assertTrue(EmailUtils.checkEmail("test@duke.edu"));

        assertTrue(EmailUtils.checkEmail(" test@duke.edu"));
        assertTrue(EmailUtils.checkEmail("test@duke.edu "));
        assertTrue(EmailUtils.checkEmail(" test@duke.edu "));
        assertFalse(EmailUtils.checkEmail("@duke.edu"));
        assertFalse(EmailUtils.checkEmail("test@duke"));
        assertTrue(EmailUtils.checkEmail("test@wildchicken.edu.us"));
        assertFalse(EmailUtils.checkEmail("www.duke.edu"));
    }
}