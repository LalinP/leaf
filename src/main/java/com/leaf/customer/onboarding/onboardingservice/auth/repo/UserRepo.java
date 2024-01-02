package com.leaf.customer.onboarding.onboardingservice.auth.repo;

import java.util.UUID;

import com.leaf.customer.onboarding.onboardingservice.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {

}
