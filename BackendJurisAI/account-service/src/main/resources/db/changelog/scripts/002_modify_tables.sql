CREATE SCHEMA IF NOT EXISTS account_service;

CREATE TABLE IF NOT EXISTS account_service.accounts(
    id BIGINT PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255),
    provider INTEGER,
    provider_id VARCHAR(255),
    is_actived INTEGER,
    created_at BIGINT,
    updated_at BIGINT
);

CREATE TABLE IF NOT EXISTS account_service.users(
    account_id BIGINT,
    user_id BIGINT,
    full_name VARCHAR(255),
    gender INTEGER,
    date_of_birth VARCHAR(15),
    user_role INTEGER,
    created_at BIGINT,
    updated_at BIGINT,
    PRIMARY KEY (account_id,user_id)
);