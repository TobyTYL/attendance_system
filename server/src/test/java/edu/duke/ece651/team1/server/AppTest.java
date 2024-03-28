package edu.duke.ece651.team1.server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.duke.ece651.team1.shared.MyName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private App app;

    @Test
    public void contextLoads() throws Exception {
        assertThat(app).isNotNull(); // Check that the app context loads and the controller is not null
    }

    @Test
    public void testIndexEndpoint() throws Exception {
        // Perform a GET request to the "/" endpoint and expect a 200 OK status
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(content().string("Greetings from Spring Boot!"));
    }

    @Test
    public void testGetMessage() {
        // Directly test the getMessage method of the App class
        String expectedMessage = "Hello from the server for " + MyName.getName();
        assertThat(app.getMessage()).isEqualTo(expectedMessage);
    }
}
