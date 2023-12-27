package com.leaf.customer.onboarding.onboardingservice.auth.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OtpRequestDto {

  @JsonProperty("country_code")
  @NotBlank(message = "CC cannot be blank")
  private String countryCode;
  @NotBlank(message = "PN cannot be blank")
  @JsonProperty("phone_number")
  private String phoneNumber;
  @JsonProperty("device_id")
  @NotBlank(message = "DID cannot be blank")
  private String deviceId;
  @JsonProperty("device_name")
  @NotBlank(message = "DN cannot be blank")
  private String deviceName;

}
