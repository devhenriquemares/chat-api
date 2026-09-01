CREATE TABLE friend_requests (
    id id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    sender_id UUID NOT NULL REFERENCES user_accounts(id),
    recipient_id UUID NOT NULL REFERENCES user_accounts(id),

    CHECK (sender_id <> recipient_id)
    UNIQUE (sender_id, recipient_id)
);