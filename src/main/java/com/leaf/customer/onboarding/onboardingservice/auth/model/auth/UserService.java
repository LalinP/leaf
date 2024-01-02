package com.leaf.customer.onboarding.onboardingservice.auth.model.auth;

import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.leaf.customer.onboarding.onboardingservice.auth.entity.User;
import com.leaf.customer.onboarding.onboardingservice.auth.repo.UserRepo;
import com.leaf.customer.onboarding.onboardingservice.auth.service.jwt.JwtHelper;
import com.leaf.customer.onboarding.onboardingservice.enums.Roles;
import com.leaf.customer.onboarding.onboardingservice.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtHelper jwtHelper;

  public RegisterResponseDto registerUser(RegisterRequestDto dto) {
    var userForSaving = User.builder()
        .userId(UUID.fromString(dto.userId()))
        .userScope("CUSTOMER")
        .userStatus(Status.ACTIVE)
        .role(Roles.USER)
        .deviceCode(dto.deviceId())
        .passcode(passwordEncoder.encode(dto.passcode()))
        .deviceName(dto.deviceName())
        .build();

       var saved =  userRepo.save(userForSaving);
        var accessToken = jwtHelper.generateJwt(saved.getUserId().toString(), "leaf_mobile_*", 90, ChronoUnit.MINUTES);
        var refreshToken = jwtHelper.generateJwt(saved.getUserId().toString(), "leaf_mobile_*", 90, ChronoUnit.DAYS);
        return new RegisterResponseDto(dto.userId(), dto.requestId(), accessToken, "Bearer", refreshToken, "90");
  }
}
