CREATE TABLE usuario
(
    id    SERIAL       NOT NULL,
    nome  VARCHAR(200) NOT NULL,
    email VARCHAR(320) NOT NULL,
    senha VARCHAR(60)  NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    CONSTRAINT pk_usuario PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_email
    ON usuario (email);
