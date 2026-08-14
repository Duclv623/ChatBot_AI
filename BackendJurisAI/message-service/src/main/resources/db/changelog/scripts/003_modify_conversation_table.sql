
ALTER TABLE message_service.conversations
    DROP CONSTRAINT conversations_pkey;

ALTER TABLE message_service.conversations
    DROP COLUMN index_conv;

ALTER TABLE message_service.conversations
    ADD PRIMARY KEY (account_id, conversations_id);