ALTER TABLE users ADD COLUMN photo_provider VARCHAR(32) NOT NULL DEFAULT 'ZAIBALO';
UPDATE users SET photo_provider = 'ZAIBALO';

ALTER TABLE users MODIFY email VARCHAR(100);

UPDATE users u
SET u.photo = REPLACE(u.photo, 'http://zaibalo.com.ua/image/', 'https://s3.eu-central-1.amazonaws.com/z-avatars/');