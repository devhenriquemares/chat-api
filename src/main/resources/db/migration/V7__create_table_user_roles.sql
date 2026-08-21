CREATE TABLE user_roles {
    id UUID PRIMARY KEY,
    user_account_id UUID NOT NULL REFERENCES user_accounts(id),
    role_id BIGINT NOT NULL REFERENCES roles(id),

    UNIQUE (user_account_id, role_id)
}