UPDATE categories SET type='CATEGORY' WHERE type='category';
UPDATE categories SET type='TAG' WHERE type='post_tag';

ALTER TABLE categories DROP COLUMN post_title;
ALTER TABLE categories DROP COLUMN user_display_name;

ALTER TABLE comment_rating DROP COLUMN post_id;
ALTER TABLE comment_rating DROP COLUMN post_title;
ALTER TABLE comment_rating DROP COLUMN user_display_name;

ALTER TABLE comments DROP COLUMN post_title;

ALTER TABLE post_rating DROP COLUMN post_title;
ALTER TABLE post_rating DROP COLUMN user_display_name;