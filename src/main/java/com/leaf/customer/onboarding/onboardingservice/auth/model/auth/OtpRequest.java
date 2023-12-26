package com.leaf.customer.onboarding.onboardingservice.auth.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OtpRequest {

  @JsonProperty("country_code")
  private String countryCode;
  @JsonProperty("phone_number")
  private String phoneNumber;
  @JsonProperty("device_id")
  private String deviceId;
  @JsonProperty("device_name")
  private String deviceName;

}
