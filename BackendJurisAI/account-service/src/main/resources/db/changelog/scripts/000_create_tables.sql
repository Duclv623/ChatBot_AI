-- CREATE SCHEMA IF NOT EXISTS account_service;
--
-- CREATE TABLE IF NOT EXISTS account_service.users(
--     id BIGINT PRIMARY KEY,
--     email VARCHAR(255) UNIQUE NOT NULL,
--     password VARCHAR(255),
--     full_name VARCHAR(255),
--     gender INTEGER CHECK (gender IN (0,1,2)),
--     user_role INTEGER CHECK (user_role IN (0,1)),
--     provider INTEGER CHECK (provider IN (0,1)),
--     provider_id VARCHAR(255),
--     is_actived INTEGER CHECK(is_actived IN (0,1)),
--     created_at BIGINT,
--     updated_at BIGINT
-- );

-- CREATE TABLE IF NOT EXISTS account_service.images(
--     account_id BIGINT,
--     image_id BIGINT,
--     image_name VARCHAR(255),
--     image_type VARCHAR(255),
--     image BYTEA,
--     download_url VARCHAR(512),
--     PRIMARY KEY(account_id, image_id)
-- );



