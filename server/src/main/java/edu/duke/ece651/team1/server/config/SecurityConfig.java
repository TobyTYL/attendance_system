package edu.duke.ece651.team1.server.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${jasypt.encryptor.password}")
    private String encryptionKey;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .authorizeRequests()
            .antMatchers("/" , "/api/signup").permitAll() 
            .antMatchers("/admin/**").hasRole("ADMIN") // Only allow users with the "ADMIN" role to access "/admin/**"
            .anyRequest().authenticated() // Require authentication for any other request
        .and()
        .formLogin()
            .permitAll().loginProcessingUrl("/api/login").successHandler((request, response, authentication) -> {
                
                response.setStatus(HttpStatus.FOUND.value()); 
                // response.setHeader("Location", "/home"); 
            })
             .failureHandler((request, response, exception) -> {
                response.setStatus(HttpStatus.UNAUTHORIZED.value()); // Set status to 401 on failure
                response.getWriter().write("Login failed: " + exception.getMessage());
            })
       ; 

    return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
   
    


}
