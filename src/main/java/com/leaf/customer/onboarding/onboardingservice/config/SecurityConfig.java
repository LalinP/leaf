package com.leaf.customer.onboarding.onboardingservice.config;

import com.leaf.customer.onboarding.onboardingservice.auth.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

  private final UserDetailsServiceImpl userDetailsService;
  /**
   * uses a salt to generate the password.
   * @return PasswordEncoder instance
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
      return httpSecurity
          .cors(AbstractHttpConfigurer::disable)
          .csrf(AbstractHttpConfigurer::disable)
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(auth -> auth
              .requestMatchers(HttpMethod.POST, "/api/v1/auth/signup/**").permitAll()
              .requestMatchers(HttpMethod.POST, "/api/v1/auth/otp/send").permitAll()
              .requestMatchers(HttpMethod.POST, "/api/v1/auth/login/**").permitAll()
              .anyRequest().authenticated())
          .authenticationManager(authenticationManager).build();

  }

  @Bean
  AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
    //Add the UserDetails service and the encoder
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    return authenticationManagerBuilder.build();
  }

  /**
   * The security Flow. All of this happens before the request hits the Dispatch Servlet
   * Authentication Filter: The UsernamePasswordAuthenticationFilter handles the extraction of the un and pw
   * from the Http request and create an authentication token
   * AuthenticationManager: Gets the request from the filter, and forwards the requeat to the auth providers for validation
   * AuthenticationProvider: validates user details
   * UserDetailsService: loads user details and match user with the given input
   */

}
