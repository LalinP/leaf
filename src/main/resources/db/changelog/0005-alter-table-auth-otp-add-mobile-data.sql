
ALTER TABLE leaf.auth_otp
add column country_code varchar(5);

ALTER TABLE leaf.auth_otp
    add column registration_mode varchar(255);

ALTER TABLE leaf.auth_otp
    add column registration_mode_type varchar(255);

ALTER TABLE leaf.auth_otp
    add column attempt_count INTEGER;