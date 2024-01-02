package com.leaf.customer.onboarding.onboardingservice.auth.service.otp;

import javax.crypto.KeyGenerator;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import com.leaf.customer.onboarding.onboardingservice.auth.entity.AuthOtp;
import com.leaf.customer.onboarding.onboardingservice.auth.model.auth.OtpRequestDto;
import com.leaf.customer.onboarding.onboardingservice.auth.model.auth.OtpResponseDto;
import com.leaf.customer.onboarding.onboardingservice.auth.model.auth.ValidateOtpResponseDto;
import com.leaf.customer.onboarding.onboardingservice.auth.repo.OtpRepo;
import com.leaf.customer.onboarding.onboardingservice.auth.service.jwt.JwtHelper;
import com.leaf.customer.onboarding.onboardingservice.enums.RegistrationModeType;
import com.leaf.customer.onboarding.onboardingservice.enums.Status;
import com.leaf.customer.onboarding.onboardingservice.exception.OtpValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;


@Slf4j
@Service
@AllArgsConstructor
public class OtpManager {
  public static final Integer OTP_EXPIRATION_IN_MINUTES = 1;
  private final PasswordEncoder passwordEncoder;
  private final OtpRepo otpRepo;
  private final JwtHelper jwtHelper;

  /**
   * The provided Java class, TimeBasedOnetimePassword,
   * implements the TOTP algorithm using HMAC-SHA1 as the cryptographic function.
   * The class also includes methods for Base32 encoding and decoding,
   * which is a common format for representing the secret key in TOTP implementations.
   *
   * TOTP Generation
   * The core of the TOTP algorithm lies in the generateTOTP methods. The process involves:
   * Decoding the Base32 encoded secret key.
   * Calculating the time interval, which is derived from the current time divided by a
   * predefined time step (30 seconds in this case).
   * Using the HMAC-SHA1 algorithm to hash the time interval with the secret key.
   * Extracting a 4-byte dynamic binary code from the hash.
   * Converting the binary code to a 6-digit number, which is the TOTP.
   * @return
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeyException
   */
  public OtpResponseDto generateNewOtp(OtpRequestDto dto) throws NoSuchAlgorithmException, InvalidKeyException {
    final TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
    final KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());
    keyGenerator.init(160);
    int otp = totp.generateOneTimePassword(keyGenerator.generateKey(),  Instant.now());
    String otpString = format("%06d", otp);
    var requestId = storeOtp(otpString, dto.getCountryCode(), passwordEncoder.encode(dto.getPhoneNumber()), dto.getDeviceId(), dto.getDeviceName());
    return new OtpResponseDto(requestId, otpString);
  }

  public ValidateOtpResponseDto validateOtp(String otp, String requestId) {
    if (isEmpty(requestId))
      throw new OtpValidationException("[OTP Validation Error]: invalid request id");

  var record = otpRepo.findByRequestId(requestId);

  if(record.getOtpStatus() == Status.EXPIRED) {
    throw new OtpValidationException("[OTP Validation Error]: Otp Expired");
  }
    if(record.getOtpStatus() == Status.VERIFIED) {
      throw new OtpValidationException("[OTP Validation Error]: Otp Already in Verified State");
    }
  boolean otpString = passwordEncoder.matches(otp, record.getOtp());
  if(otpString && record.getOtpStatus() == Status.ACTIVE && record.getCreatedDate()
      .isBefore(Instant.now().minus(OTP_EXPIRATION_IN_MINUTES, ChronoUnit.MINUTES))) {
    record.setUpdatedDate(Instant.now());
    record.setLastAttemptedDate(Instant.now());
    record.setOtpStatus(Status.VERIFIED);
    var updated = otpRepo.save(record);
    var accessToken = jwtHelper.generateJwt(updated.getUserAccountId().toString(), "leaf_onboarding_user", 10, ChronoUnit.MINUTES);
    var refreshToken = jwtHelper.generateJwt(updated.getUserAccountId().toString(), "leaf_onboarding_user", 90, ChronoUnit.DAYS);
    return new ValidateOtpResponseDto(updated.getUserAccountId().toString(),
        updated.getRequestId(),
        accessToken,
        "Bearer",
        refreshToken,
        "90" );
  } else {
    throw new BadCredentialsException("[OTP Validation Error]: Validation Error");
  }
  }
  private String storeOtp(CharSequence otp, String countryCode, String number, String deviceId, String deviceName) {
    var encryptedOtp = passwordEncoder.encode(otp);
    AuthOtp authOtp = AuthOtp.builder()
        .otpStatus(Status.ACTIVE)
        .otp(encryptedOtp)
        .userAccountId(UUID.randomUUID())
        .requestId(UUID.randomUUID().toString())
        .createdDate(Instant.now())
        .updatedDate(Instant.now())
        .registrationModeType(RegistrationModeType.MOBILE)
        .attemptCount(1)
        .countryCode(countryCode)
        .registrationMode(number)
        .deviceId(deviceId)
        .deviceName(deviceName)
        .build();
    var saved = otpRepo.save(authOtp);
    return saved.getRequestId();
  }


}
