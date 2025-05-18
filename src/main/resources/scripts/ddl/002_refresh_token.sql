CREATE TABLE refresh_token
(
    id         SERIAL                      NOT NULL,
    token      VARCHAR(36)                 NOT NULL,
    user_id BIGINT                      NOT NULL,
    creationDateTime  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    expirationDateTime  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_refresh_token PRIMARY KEY (id),
    CONSTRAINT fk_user_refresh_token FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE UNIQUE INDEX idx_token ON refresh_token (token);
