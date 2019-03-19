CREATE TABLE `address` (
  `id` int(128) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `address` varchar(64) DEFAULT NULL,
  `latitude` double(10,6) DEFAULT NULL,
  `longitude` double(10,6) DEFAULT NULL,
  `type` tinyint(2) DEFAULT NULL ,
  `hot_point` int(128) DEFAULT '1');
--
-- CREATE TABLE `pin_order` (
--   `id` int(128) unsigned NOT NULL AUTO_INCREMENT,
--   `gmt_create` datetime DEFAULT NULL,
--   `gmt_modified` datetime DEFAULT NULL,
--   `creator` int(128) DEFAULT NULL,
--   `leader` int(128) DEFAULT NULL,
--   `start_address_id` int(128) NOT NULL,
--   `end_address_id` int(128) NOT NULL,
--   `target_time` datetime NOT NULL,
--   `target_num` int(32) DEFAULT NULL,
--   `current_num` int(32) DEFAULT '1',
--   `deleted` tinyint(2) DEFAULT '0',
--   PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=257 DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `room_message` (
--   `id` int(128) unsigned NOT NULL AUTO_INCREMENT,
--   `user_id` int(128) DEFAULT NULL,
--   `time` varchar(32) DEFAULT NULL,
--   `message` varchar(256) CHARACTER SET utf8mb4 DEFAULT NULL,
--   `order_id` int(128) DEFAULT NULL COMMENT 'orderid=roomid',
--   PRIMARY KEY (`id`),
--   KEY `orderid` (`order_id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `user` (
--   `id` int(128) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
--   `gmt_create` datetime DEFAULT NULL,
--   `gmt_modified` datetime DEFAULT NULL,
--   `last_login_time` datetime DEFAULT NULL,
--   `openid` varchar(64) NOT NULL,
--   `nick_name` varchar(64) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '用户名称',
--   `gender` tinyint(3) unsigned DEFAULT '0' COMMENT '0未知1男2女',
--   `avatar_url` varchar(256) DEFAULT '',
--   `country` varchar(256) DEFAULT NULL,
--   `province` varchar(32) DEFAULT NULL,
--   `city` varchar(32) DEFAULT NULL,
--   `language` varchar(8) DEFAULT NULL,
--   `school` varchar(32) DEFAULT NULL,
--   `phone` int(16) DEFAULT NULL,
--   `deleted` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '0存在1删除',
--   PRIMARY KEY (`id`),
--   UNIQUE KEY `openid` (`openid`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=473 DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `user_order_rel` (
--   `id` int(128) unsigned NOT NULL AUTO_INCREMENT,
--   `gmt_create` datetime DEFAULT NULL,
--   `gmt_modified` datetime DEFAULT NULL,
--   `order_id` int(128) DEFAULT NULL,
--   `user_id` int(128) DEFAULT NULL,
--   `leader` tinyint(4) NOT NULL COMMENT '0队员1队长',
--   `deleted` tinyint(2) DEFAULT '0' COMMENT '0存在1不存在',
--   PRIMARY KEY (`id`),
--   UNIQUE KEY `order_id` (`order_id`,`user_id`,`deleted`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=365 DEFAULT CHARSET=utf8;