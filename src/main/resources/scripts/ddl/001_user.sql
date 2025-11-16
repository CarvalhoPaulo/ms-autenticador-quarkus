CREATE TABLE "user"
(
    id       BIGSERIAL    NOT NULL,
    name     VARCHAR(200) NOT NULL,
    username VARCHAR(200) NOT NULL,
    email    VARCHAR(320) NOT NULL,
    password VARCHAR(60)  NOT NULL,
    active   BOOLEAN DEFAULT TRUE,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_email
    ON "user" (email);
CREATE UNIQUE INDEX idx_username
    ON "user" (username);
