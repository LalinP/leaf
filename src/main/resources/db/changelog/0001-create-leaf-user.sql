CREATE SCHEMA IF NOT EXISTS leaf;

CREATE TABLE IF NOT EXISTS leaf.leaf_user
(
    userId                            UUID NOT NULL DEFAULT  gen_random_uuid(),
    country_code                      VARCHAR(255),
    mobile_number                     VARCHAR(255),
    passcode                          VARCHAR(6),
    device_id                         VARCHAR(255),
    user_status                          VARCHAR(255),
    role                                 VARCHAR(255),
    created_date                    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_date                    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT document_job_pkey    PRIMARY KEY (userId)
);