CREATE TABLE email_codes (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    code VARCHAR(6) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ DEFAULT now(),
    expires_at TIMESTAMPTZ NOT NULL,
    is_expired BOOLEAN DEFAULT false,

    code_owner_id UUID NOT NULL REFERENCES user_accounts(id)
);