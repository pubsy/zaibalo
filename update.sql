
ALTER TABLE users MODIFY COLUMN role varchar(32) not null default "ROLE_USER";

UPDATE users set role="ROLE_ADMIN" WHERE id = 1;
UPDATE users set role="ROLE_USER" WHERE id != 1;

ALTER TABLE categories DROP COLUMN type;

delete from post_category where category_id in (530, 543, 542, 541, 540, 392);
delete from categories where id in (530, 543, 542, 541, 540, 392);
UPDATE categories SET name = concat('#',name) where name not like '#%';
UPDATE posts set content = concat(content, '\n');
UPDATE posts p set content = concat(content, (select GROUP_CONCAT(name SEPARATOR ' ') from categories where categories.id in (select category_id from post_category where post_id=p.id)));
