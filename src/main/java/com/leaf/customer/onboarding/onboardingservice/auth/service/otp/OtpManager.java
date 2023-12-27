package com.leaf.customer.onboarding.onboardingservice.auth.service.otp;

import javax.crypto.KeyGenerator;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.UUID;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import com.leaf.customer.onboarding.onboardingservice.auth.entity.AuthOtp;
import com.leaf.customer.onboarding.onboardingservice.auth.model.auth.OtpResponseDto;
import com.leaf.customer.onboarding.onboardingservice.auth.repo.OtpRepo;
import com.leaf.customer.onboarding.onboardingservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class OtpManager {

  private final PasswordEncoder passwordEncoder;
  private final OtpRepo otpRepo;
  public OtpResponseDto generateNewOtp() throws NoSuchAlgorithmException, InvalidKeyException {
    final TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
    final KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());
    keyGenerator.init(160);
    int otp = totp.generateOneTimePassword(keyGenerator.generateKey(),  Instant.now());
    String otpString = String.format("%06d", otp);
    log.debug("otp: {}", otpString);
    var requestId = storeOtp(otpString);
    return new OtpResponseDto(requestId, otpString);
  }

  private String storeOtp(CharSequence otp) {
    var encryptedOtp = passwordEncoder.encode(otp);
    AuthOtp authOtp = AuthOtp.builder()
        .otpStatus(Status.ACTIVE)
        .otp(encryptedOtp)
        .userAccountId(UUID.randomUUID())
        .requestId(UUID.randomUUID().toString())
        .build();
    var saved = otpRepo.save(authOtp);
    return saved.getRequestId();
  }
}
