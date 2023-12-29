package com.leaf.customer.onboarding.onboardingservice.auth.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ValidateOtpRequestDto(@JsonProperty("otp")
                           String otp,
                                    @JsonProperty("request_id")
                           String requestId) {

}
