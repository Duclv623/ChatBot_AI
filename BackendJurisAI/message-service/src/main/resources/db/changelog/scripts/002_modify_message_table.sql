
ALTER TABLE message_service.messages
DROP CONSTRAINT messages_pkey;

ALTER TABLE message_service.messages
ADD PRIMARY KEY (account_id, conversations_id, message_id);