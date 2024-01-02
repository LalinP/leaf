package com.leaf.customer.onboarding.onboardingservice.auth.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import com.leaf.customer.onboarding.onboardingservice.enums.Roles;
import com.leaf.customer.onboarding.onboardingservice.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "leaf")
public class User implements Serializable {

  @Id
  private UUID userId;
  private String countryCode;
  private String mobileNumber;
  private String passcode;
  private String deviceCode;
  private String deviceName;
  @Enumerated(EnumType.STRING)
  private Status userStatus;
  @Enumerated(EnumType.STRING)
  private Roles role;
  private String userScope;
  @Column(name = "created_date")
  private Instant createdDate;
  @Column(name = "updated_date")
  private Instant updatedDate;

  public void setId(UUID userId) {
    this.userId = userId;
  }

  public UUID getId() {
    return userId;
  }
}
