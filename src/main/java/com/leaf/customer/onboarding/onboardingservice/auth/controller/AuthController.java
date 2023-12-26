package com.leaf.customer.onboarding.onboardingservice.auth.controller;

import com.leaf.customer.onboarding.onboardingservice.auth.controller.dto.ApiErrorResponse;
import com.leaf.customer.onboarding.onboardingservice.auth.model.auth.OtpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {



  @Operation(summary = "Send OTP to User")
  @ApiResponse(responseCode = "200")
  @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
  @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
  @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
  @PostMapping("/otp/send")
  public ResponseEntity<String> sendOtp(@Valid @RequestBody OtpRequest otpRequest, HttpHeaders httpHeaders) {
    return new ResponseEntity<>("Please check your SMS folder for the OTP", HttpStatus.CREATED);
  }
}
