CREATE TABLE iam_user(
    id          INTEGER
        PRIMARY KEY ,
    username    VARCHAR(255)                                NOT NULL
        UNIQUE,
    displayname VARCHAR(255)                                NOT NULL
        UNIQUE,
    enabled     BOOLEAN         DEFAULT TRUE                NOT NULL,
    created_at  TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   NOT NULL,
    updated_at  TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   NOT NULL
);
CREATE TRIGGER trigger_set_updated_at
    AFTER UPDATE ON iam_user
    FOR EACH ROW
    WHEN NEW.updated_at = OLD.updated_at
BEGIN
    UPDATE iam_user
    SET updated_at = CURRENT_TIMESTAMP
    WHERE id = OLD.id;
END;


CREATE TABLE iam_credential(
    user_id                  INTEGER
        PRIMARY KEY
        REFERENCES iam_user
            ON DELETE CASCADE,
    password_hash            VARCHAR(255)                               NOT NULL,
    expired                  BOOLEAN        DEFAULT FALSE               NOT NULL,
    password_last_changed_at TIMESTAMP      DEFAULT CURRENT_TIMESTAMP   NOT NULL
);

