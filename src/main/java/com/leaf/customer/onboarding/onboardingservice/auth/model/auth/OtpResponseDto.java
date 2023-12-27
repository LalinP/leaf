package com.leaf.customer.onboarding.onboardingservice.auth.model.auth;


import com.fasterxml.jackson.annotation.JsonProperty;

public record OtpResponseDto( @JsonProperty("request_id")
                             String requestId,
                             @JsonProperty("otp")
                             String Otp) {}
