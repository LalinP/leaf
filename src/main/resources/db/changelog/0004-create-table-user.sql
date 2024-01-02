
CREATE TABLE IF NOT EXISTS leaf.user
(
    userId                            UUID NOT NULL,
    country_code                      VARCHAR(255),
    mobile_number                     VARCHAR(255),
    passcode                          VARCHAR(6),
    device_code                        VARCHAR(255),
    device_name                       VARCHAR(255),
    user_status                       VARCHAR(255),
    role                              VARCHAR(255),
    user_scope                        VARCHAR(255),
    created_date                    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_date                    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT user_pkey    PRIMARY KEY (userId)
);