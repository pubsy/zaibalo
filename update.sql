ALTER TABLE users DROP COLUMN small_img_path;

UPDATE users SET big_img_path = CONCAT("http://zaibalo.com.ua/image/", big_img_path) WHERE big_img_path != NULL OR big_img_path != "";

ALTER TABLE users CHANGE COLUMN big_img_path
photo VARCHAR(256) NOT NULL DEFAULT "http://zaibalo.com.ua/image/default.jpg";

UPDATE users SET photo = "http://zaibalo.com.ua/image/default.jpg" WHERE photo = NULL OR photo = "";

