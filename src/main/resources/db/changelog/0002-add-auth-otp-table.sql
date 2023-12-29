CREATE SCHEMA IF NOT EXISTS leaf;

CREATE TABLE IF NOT EXISTS leaf.auth_otp
(
    id                              UUID NOT NULL DEFAULT  gen_random_uuid(),
    request_id                      VARCHAR(255),
    user_account_id                 UUID NOT NULL,
    otp                             VARCHAR(255) NOT NULL,
    otp_status                      VARCHAR(255) DEFAULT 'ACTIVE',
    created_date                    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_date                    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_attempted_date             TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT auth_otp_pkey    PRIMARY KEY (id)
);