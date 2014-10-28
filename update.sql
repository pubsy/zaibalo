
ALTER TABLE users MODIFY COLUMN role varchar(32) not null default "ROLE_USER";

UPDATE users set role="ROLE_ADMIN" WHERE id = 1;
UPDATE users set role="ROLE_USER" WHERE id! = 1;