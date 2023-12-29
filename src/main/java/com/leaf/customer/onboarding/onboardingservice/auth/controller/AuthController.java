package com.leaf.customer.onboarding.onboardingservice.auth.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.leaf.customer.onboarding.onboardingservice.auth.controller.dto.ApiErrorResponse;
import com.leaf.customer.onboarding.onboardingservice.auth.model.auth.ValidateOtpRequestDto;
import com.leaf.customer.onboarding.onboardingservice.auth.model.auth.ValidateOtpResponseDto;
import com.leaf.customer.onboarding.onboardingservice.auth.model.auth.OtpRequestDto;
import com.leaf.customer.onboarding.onboardingservice.auth.model.auth.OtpResponseDto;
import com.leaf.customer.onboarding.onboardingservice.auth.service.jwt.JwtHelper;
import com.leaf.customer.onboarding.onboardingservice.auth.service.otp.OtpManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

private final JwtHelper jwtHelper;
private final OtpManager otpManager;

  @Operation(summary = "Send OTP to User")
  @ApiResponse(responseCode = "201")
  @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
  @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
  @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
  @PostMapping("/otp/send")
  public ResponseEntity<OtpResponseDto> sendOtp(@Valid @RequestBody OtpRequestDto otpRequestDto) {
    var number = otpRequestDto.getPhoneNumber();
    OtpResponseDto otp;
    try {
      otp = otpManager.generateNewOtp();
      log.debug("OTP {}", otp.Otp());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    } catch (InvalidKeyException e) {
      throw new RuntimeException(e);
    }
    return new ResponseEntity<>(otp, HttpStatus.CREATED);
  }

  @Operation(summary = "Validate the OTP")
  @ApiResponse(responseCode = "202")
  @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
  @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
  @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
  @PostMapping("/login")
  public ResponseEntity<ValidateOtpResponseDto> validateOtp(@Valid @RequestBody ValidateOtpRequestDto validateOtpRequestDto) {
  var response = otpManager.validateOtp(validateOtpRequestDto.otp(), validateOtpRequestDto.requestId());
    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
  }

 /* @Operation(summary = "Login using OTP")
  @ApiResponse(responseCode = "200")
  @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
  @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
  @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
  @PostMapping("/login")
  public ResponseEntity<ValidateOtpResponseDto> login(@Valid @RequestBody ValidateOtpRequestDto loginRequest) {

    ValidateOtpResponseDto dto = new ValidateOtpResponseDto("1", "test", "test");
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
  }*/
}
