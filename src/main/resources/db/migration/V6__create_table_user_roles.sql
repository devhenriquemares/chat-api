CREATE TABLE user_roles {
    id UUID PRIMARY KEY,
    user_account_id UUID NOT NULL REFERENCES user_accounts(id),
    role VARCHAR(15) NOT NULL,

    UNIQUE (user_account_id, role)
}