package com.leaf.customer.onboarding.onboardingservice.auth.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(
    @JsonProperty("user_id")
    @NotBlank(message = "Id cannot be blank")
    String userId,

    @JsonProperty("request_id")
    @NotBlank(message = "request Id cannot be blank")
    String requestId,
    @JsonProperty("country_code")
    @NotBlank(message = "CC cannot be blank")
    String countryCode,
    @JsonProperty("phone_number")
    @NotBlank(message = "Mobile number cannot be blank")
    String mobileNumber,
    @JsonProperty("device_id")
    @NotBlank(message = "DID cannot be blank")
    String deviceId,
    @JsonProperty("device_name")
    @NotBlank(message = "DN cannot be blank")
    String deviceName,
    @JsonProperty("passcode")
    @NotBlank(message = "Passcode cannot be blank")
    String passcode,
    @JsonProperty("first_name")
    @NotBlank(message = "Firstname cannot be blank")
    String firstname,
    @JsonProperty("last_name")
    @NotBlank(message = "last name cannot be blank")
    String lastname,
    @JsonProperty("email")
    @NotBlank(message = "Email cannot be blank")
    String email) {

}
