CREATE TABLE user_accounts (
    id UUID PRIMARY KEY,
    public_id VARCHAR(10) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    is_verified BOOLEAN DEFAULT false,
    password_hash VARCHAR(255),
    password_salt VARCHAR(32),
    provider VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT now()
);