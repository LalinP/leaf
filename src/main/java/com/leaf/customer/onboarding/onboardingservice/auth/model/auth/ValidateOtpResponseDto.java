package com.leaf.customer.onboarding.onboardingservice.auth.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ValidateOtpResponseDto(@JsonProperty("user_id")
                                      String userId,
                                     @JsonProperty("request_id")
                                     String requestId,
                                     @JsonProperty("access_token")
                                      String accessToken,
                                     @JsonProperty("token_type")
                                     String tokenType,
                                     @JsonProperty("refresh_token")String refreshToken,
                                     @JsonProperty("expires_in_days") String expiresInDays ) {

}
