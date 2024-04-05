// package edu.duke.ece651.team1.server.controller;

// import static org.mockito.Mockito.doNothing;

// import edu.duke.ece651.team1.server.service.UserService;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.web.servlet.ResultActions;
// import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// @ContextConfiguration(classes = {SecurityController.class})
// @ExtendWith(SpringExtension.class)
// class SecurityControllerTest {
//     @Autowired
//     private SecurityController securityController;

//     @MockBean
//     private UserService userService;

//     @Test
//     void testAdminPage() throws Exception {
//         MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/admin");
//         MockMvcBuilders.standaloneSetup(securityController)
//                 .build()
//                 .perform(requestBuilder)
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
//                 .andExpect(MockMvcResultMatchers.content().string("Welcome to admin page!"));
//     }

//     @Test
//     void testAdminPage2() throws Exception {
//         // Arrange
//         MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/admin", "Uri Variables");

//         // Act and Assert
//         MockMvcBuilders.standaloneSetup(securityController)
//                 .build()
//                 .perform(requestBuilder)
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
//                 .andExpect(MockMvcResultMatchers.content().string("Welcome to admin page!"));
//     }

//     @Test
//     void testPostMethodName() throws Exception {
//         doNothing().when(userService).createUser(Mockito.<String>any(), Mockito.<String>any());
//         MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/signup")
//                 .param("password", "foo")
//                 .param("username", "foo");
//         ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(securityController)
//                 .build()
//                 .perform(requestBuilder);
//         actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
//                 .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
//                 .andExpect(MockMvcResultMatchers.content().string("Congrat! You have successfully signed up"));
//     }

//     @Test
//     void testTest() throws Exception {
//         MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/test");
//         MockMvcBuilders.standaloneSetup(securityController)
//                 .build()
//                 .perform(requestBuilder)
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
//                 .andExpect(MockMvcResultMatchers.content().string("Welcome to admin page!"));
//     }

//     @Test
//     void testTest2() throws Exception {
//         MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/test");
//         requestBuilder.characterEncoding("Encoding");
//         MockMvcBuilders.standaloneSetup(securityController)
//                 .build()
//                 .perform(requestBuilder)
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
//                 .andExpect(MockMvcResultMatchers.content().string("Welcome to admin page!"));
//     }

// }
