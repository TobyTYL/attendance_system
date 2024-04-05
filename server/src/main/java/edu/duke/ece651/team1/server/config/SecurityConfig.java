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

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${jasypt.encryptor.password}")
    private String encryptionKey;
    @Autowired
    private  UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/api/signup/student", "/api/signup/professor").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN") // Only allow users with the "ADMIN" role to access
                                                           // "/admin/**"
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

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

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
