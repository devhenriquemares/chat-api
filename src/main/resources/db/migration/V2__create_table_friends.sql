CREATE TABLE friends (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_account_id UUID NOT NULL REFERENCES user_accounts(id),
    friend_account_id UUID NOT NULL REFERENCES user_accounts(id),

    CHECK (user_account_id <> friend_account_id),
    UNIQUE (user_account_id, friend_account_id)
);