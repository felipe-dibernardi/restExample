/* Create Username & Password on table usuario */

ALTER TABLE usuario ADD COLUMN username VARCHAR(32) NOT NULL;
ALTER TABLE usuario ADD COLUMN senha VARCHAR(64) NOT NULL;
ALTER TABLE usuario ADD COLUMN profile VARCHAR(32) NOT NULL;

/* Create View for authentication */

CREATE VIEW vi_usuario_profile AS SELECT username, profile FROM usuario;