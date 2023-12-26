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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "leaf_user", schema = "leaf")
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID userId;
  private String countryCode;
  private String mobileNumber;
  private String passcode;
  private String deviceId;
  @Enumerated(EnumType.STRING)
  private Status userStatus;
  @Enumerated(EnumType.STRING)
  private Roles role;
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
