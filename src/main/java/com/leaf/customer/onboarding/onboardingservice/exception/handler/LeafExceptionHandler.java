package com.leaf.customer.onboarding.onboardingservice.exception.handler;

import com.leaf.customer.onboarding.onboardingservice.auth.controller.dto.ApiErrorResponse;
import com.leaf.customer.onboarding.onboardingservice.exception.OtpValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@ControllerAdvice
public class LeafExceptionHandler {
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException exception) {
    return ResponseEntity.status(FORBIDDEN)
        .body(new ApiErrorResponse(FORBIDDEN.value(), exception.getMessage()));
  }

  @ExceptionHandler(OtpValidationException.class)
  public ResponseEntity<ApiErrorResponse> handleOtpValidationException(OtpValidationException exception) {
    return ResponseEntity.status(FORBIDDEN).body((new ApiErrorResponse(FORBIDDEN.value(), exception.getMessage())));
  }
}
