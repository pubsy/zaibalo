CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL DEFAULT '',
  `display_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` int(11) NOT NULL DEFAULT '2',
  `date` datetime DEFAULT NULL,
  `token` varchar(100) NOT NULL,
  `small_img_path` varchar(100) DEFAULT NULL,
  `big_img_path` varchar(100) DEFAULT NULL,
  `about` varchar(100) DEFAULT '-',
  `notify_on_pm` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `display_name` (`display_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `content` text,
  `author_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `rating_count` int(11) DEFAULT '0',
  `rating_sum` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `author_fk` (`author_id`),
  CONSTRAINT `author_fk` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  `author_id` int(11) DEFAULT NULL,
  `post_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `post_title` varchar(100) NOT NULL,
  `rating_count` int(11) DEFAULT '0',
  `rating_sum` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `postfk` (`post_id`),
  KEY `com_author_fk` (`author_id`),
  CONSTRAINT `com_author_fk` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`),
  CONSTRAINT `postfk` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `comment_rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `comment_id` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  `post_title` varchar(100) NOT NULL,
  `post_id` int(11) NOT NULL,
  `user_display_name` varchar(100) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `c_unique_group` (`user_id`,`comment_id`),
  KEY `c_rt_comment_fk` (`comment_id`),
  KEY `c_rt_author_fk` (`user_id`),
  KEY `c_rt_post_fk` (`post_id`),
  CONSTRAINT `c_rt_author_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `c_rt_comment_fk` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `c_rt_post_fk` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `discussions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `extract` text NOT NULL,
  `author_id` int(11) NOT NULL,
  `recipient_id` int(11) NOT NULL,
  `latest_message_date` datetime DEFAULT NULL,
  `has_unread_messages` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `author_id` (`author_id`),
  KEY `recipient_id` (`recipient_id`),
  CONSTRAINT `dis_author_fk` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`),
  CONSTRAINT `dis_recipient_fk` FOREIGN KEY (`recipient_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` text NOT NULL,
  `author_id` int(11) NOT NULL,
  `recipient_id` int(11) NOT NULL,
  `creation_date` datetime DEFAULT NULL,
  `is_read` tinyint(1) DEFAULT '1',
  `discussion_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mes_author_fk` (`author_id`),
  KEY `mes_recipient_fk` (`recipient_id`),
  KEY `mes_discuss_fk` (`discussion_id`),
  CONSTRAINT `mes_author_fk` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`),
  CONSTRAINT `mes_discuss_fk` FOREIGN KEY (`discussion_id`) REFERENCES `discussions` (`id`),
  CONSTRAINT `mes_recipient_fk` FOREIGN KEY (`recipient_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `post_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pc_cat_fk` (`post_id`),
  KEY `pc_category_fk` (`category_id`),
  CONSTRAINT `pc_category_fk` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `pc_post_fk` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `post_rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  `post_title` varchar(100) NOT NULL,
  `user_display_name` varchar(100) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_group` (`user_id`,`post_id`),
  KEY `rt_post_fk` (`post_id`),
  KEY `rt_author_fk` (`user_id`),
  CONSTRAINT `rt_author_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `rt_post_fk` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
