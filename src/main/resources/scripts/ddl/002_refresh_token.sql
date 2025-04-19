CREATE TABLE refresh_token
(
    id         SERIAL                      NOT NULL,
    token      VARCHAR(36)                 NOT NULL,
    id_usuario BIGINT                      NOT NULL,
    dataHoraCriacao  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    dataHoraExpiracao  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_refresh_token PRIMARY KEY (id),
    CONSTRAINT fk_usuario_refresh_token FOREIGN KEY (id_usuario) REFERENCES usuario (id)
);

CREATE UNIQUE INDEX idx_token ON refresh_token (token);
