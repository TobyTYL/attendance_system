package edu.duke.ece651.team1.server.config;
import java.util.HashMap;
import java.util.Map;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.duke.ece651.team1.data_access.Professor.ProfessorDao;
import edu.duke.ece651.team1.data_access.Professor.ProfessorDaoImp;
import edu.duke.ece651.team1.data_access.Student.StudentDao;
import edu.duke.ece651.team1.data_access.Student.StudentDaoImp;
import edu.duke.ece651.team1.server.model.CustomUserDetails;
import edu.duke.ece651.team1.server.service.UserService;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import edu.duke.ece651.team1.server.service.UserService;
/**
 * SecurityConfig configures the security settings for the web application, 
 * particularly focusing on user authentication and authorization. It utilizes Spring Security
 * to manage security operations such as login and access restrictions.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${jasypt.encryptor.password}")
    private String encryptionKey;
    @Autowired
    private  UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
     /**
     * Defines the security filter chain that specifies security settings.
     * @param http HttpSecurity configuration builder
     * @return SecurityFilterChain object that spring security will use to handle security.
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/api/signup/student", "/api/signup/professor").permitAll()
                .anyRequest().authenticated() // Require authentication for any other request
                .and()
                .formLogin()
                .permitAll().loginProcessingUrl("/api/login").successHandler(
                    successHandler()
                )
                .failureHandler((request, response, exception) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value()); // Set status to 401 on failure
                    response.getWriter().write("Login failed: " + exception.getMessage());
                });

        return http.build();

    }
    /**
     * Configures global settings for user authentication integrating with custom user service.
     * @param auth the AuthenticationManagerBuilder to build the authentication manager.
     * @throws Exception if an authentication configuration error occurs
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
    /**
     * Custom handler for successful authentication, providing additional user details and custom responses.
     * @return AuthenticationSuccessHandler instance to handle successful authentication.
     */
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            Object principal = authentication.getPrincipal();
            int uid = ((CustomUserDetails)principal).getUserId();
            String role = ((CustomUserDetails)principal).getRole();
            Map<String, Object> data = new HashMap<>();
            data.put("role", role);
            if(role.equals("Professor")){
                ProfessorDao dao = new ProfessorDaoImp();
                int professor_id = dao.findProfessorByUsrID(uid).getProfessorId();
                data.put("id", professor_id);

            }else{
                StudentDao dao = new StudentDaoImp();
                int professor_id = dao.findStudentByUserID(uid).get().getStudentId();
                data.put("id", professor_id);
            }
            response.setStatus(HttpStatus.FOUND.value());
            response.getOutputStream()
                .println(new ObjectMapper().writeValueAsString(data));
        };
    }

    

}
