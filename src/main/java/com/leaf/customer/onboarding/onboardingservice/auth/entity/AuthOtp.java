package com.leaf.customer.onboarding.onboardingservice.auth.entity;

import java.time.Instant;
import java.util.UUID;

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


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auth_otp", schema = "leaf")
public class AuthOtp {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String requestId;
  private UUID userAccountId;
  private String otp;
  @Enumerated(EnumType.STRING)
  private Status otpStatus;
  @Column(name = "created_date")
  private Instant createdDate;
  @Column(name = "updated_date")
  private Instant updatedDate;
  @Column(name ="last_attempted_date")
  private Instant lastAttemptedDate;
}
