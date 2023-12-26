package com.leaf.customer.onboarding.onboardingservice.auth.service;

import java.util.UUID;

import com.leaf.customer.onboarding.onboardingservice.auth.repo.UserRepo;
import com.leaf.customer.onboarding.onboardingservice.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

 private final UserRepo userRepo;
  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    var user = userRepo.findById(UUID.fromString(userId)).orElseThrow(() -> new EntityNotFoundException(String.format("User with Id %s doesnt exsit", userId)));

    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getId().toString())
        .password(user.getPasscode())
        .build();
  }
}
