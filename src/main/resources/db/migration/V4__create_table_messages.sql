CREATE TABLE messages (
    id UUID PRIMARY KEY,
    message TEXT NOT NULL,

    chat_id BIGINT NOT NULL REFERENCES friends(id),
    sender_id UUID NOT NULL REFERENCES user_accounts(id)
);