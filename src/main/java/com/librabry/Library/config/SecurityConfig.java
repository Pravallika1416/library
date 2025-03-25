package com.librabry.Library.config;

import com.librabry.Library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Autowired
    private UserService userService;
    @Autowired
    private CommonConfig commonConfig;
    @Value("${student.authority}")
    private String studentAuthority;
    @Value("${admin.authority}")
    private String adminAuthority;
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(commonConfig.getEncoder());
        return authenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/addStudent/**").permitAll()
                        .requestMatchers("/user/addAdmin/**").permitAll()
                        .requestMatchers("/user/filter/**").hasAnyAuthority(studentAuthority,adminAuthority)
                        .requestMatchers("/txn/**").hasRole(adminAuthority)
                        .requestMatchers("/book/addBook/**").hasRole(adminAuthority)
                        .requestMatchers("/book/filter/**").hasAnyAuthority(studentAuthority,adminAuthority)
                        .anyRequest().authenticated()
                ).formLogin(withDefaults()).httpBasic(withDefaults()).csrf(csrf->csrf.disable());
        return http.build();
    }
//    @Bean...........due to cirCULAR DEPENDENCY
//    public PasswordEncoder getEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }

}
