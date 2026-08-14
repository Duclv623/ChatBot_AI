CREATE SCHEMA IF NOT EXISTS message_service;

CREATE TABLE IF NOT EXISTS message_service.conversations (
    account_id BIGINT,
    conversations_id BIGINT,
    title TEXT,
    parent_conv_id BIGINT NOT NULL,
    index_conv INTEGER NOT NULL,
    status INTEGER ,
    created_at BIGINT,
    updated_at BIGINT,
    PRIMARY KEY (account_id, conversations_id, parent_conv_id)
);

CREATE TABLE  IF NOT EXISTS message_service.messages (
    account_id BIGINT,
    conversations_id BIGINT,
    message_id BIGINT,
    parent_messages_id BIGINT,
    index_messages INTEGER,
    model_chat TEXT,
    sender INTEGER,
    content TEXT,
    file_uuid BIGINT,
    status INTEGER,
    created_at BIGINT,
    updated_at BIGINT,
    PRIMARY KEY (account_id, conversations_id, message_id, parent_messages_id)
);

CREATE TABLE  IF NOT EXISTS message_service.citations (
    account_id BIGINT,
    message_id BIGINT,
    citation_id BIGINT,
    law_name TEXT,
    chapter TEXT,
    section TEXT,
    article TEXT,
    created_at BIGINT,
    updated_at BIGINT,
    PRIMARY KEY (account_id, message_id, citation_id )
);
