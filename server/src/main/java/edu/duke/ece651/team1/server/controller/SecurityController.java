package edu.duke.ece651.team1.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import edu.duke.ece651.team1.server.service.UserService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class SecurityController {
     private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);
    @Autowired
    UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<String> postMethodName(@RequestParam(value = "username") String userName,
            @RequestParam(value = "password") String password) {
        //TODO: process POST request
        logger.info("test");
        userService.createUser(userName, password);
       
        return new ResponseEntity<String>("Congrat! You have successfully signed up", HttpStatus.CREATED);
    }
    @GetMapping("/admin")
    // @PreAuthorize("hasRole('ADMIN')")
    public String adminPage() {
        return "Welcome to admin page!"; 
    }
    @GetMapping("/test")
    public String test() {
        return "Welcome to admin page!"; 
    }
    // @GetMapping("/login")
    // public String login() {
    //     return "login"; // 返回Spring Security默认的登录表单视图
    // }
   
   
    
}
