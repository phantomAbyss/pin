/*
 Navicat Premium Data Transfer

 Source Server         : tencent
 Source Server Type    : MySQL
 Source Server Version : 50651
 Source Host           : localhost:3306
 Source Schema         : pin

 Target Server Type    : MySQL
 Target Server Version : 50651
 File Encoding         : 65001

 Date: 16/12/2021 18:23:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int(128) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime NULL DEFAULT NULL,
  `gmt_modified` datetime NULL DEFAULT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `latitude` double(10, 6) NULL DEFAULT NULL,
  `longitude` double(10, 6) NULL DEFAULT NULL,
  `type` tinyint(2) NULL DEFAULT NULL COMMENT '0起点1终点',
  `hot_point` int(128) NULL DEFAULT 1 COMMENT '使用次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni`(`latitude`, `longitude`, `type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pin_order
-- ----------------------------
DROP TABLE IF EXISTS `pin_order`;
CREATE TABLE `pin_order`  (
  `id` int(128) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime NULL DEFAULT NULL,
  `gmt_modified` datetime NULL DEFAULT NULL,
  `creator` int(128) NULL DEFAULT NULL,
  `leader` int(128) NULL DEFAULT NULL,
  `start_address_id` int(128) NOT NULL,
  `end_address_id` int(128) NOT NULL,
  `target_time` datetime NOT NULL,
  `target_num` int(32) NULL DEFAULT NULL,
  `current_num` int(32) NULL DEFAULT 1,
  `deleted` tinyint(2) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for room_message
-- ----------------------------
DROP TABLE IF EXISTS `room_message`;
CREATE TABLE `room_message`  (
  `id` int(128) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(128) NULL DEFAULT NULL,
  `time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `message` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `order_id` int(128) NULL DEFAULT NULL COMMENT 'orderid=roomid',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orderid`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(128) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NULL DEFAULT NULL,
  `gmt_modified` datetime NULL DEFAULT NULL,
  `last_login_time` datetime NULL DEFAULT NULL,
  `openid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户名称',
  `gender` tinyint(3) UNSIGNED NULL DEFAULT 0 COMMENT '0未知1男2女',
  `avatar_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `country` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `province` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `language` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `school` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` int(16) NULL DEFAULT NULL,
  `deleted` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0存在1删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `openid`(`openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_order_rel
-- ----------------------------
DROP TABLE IF EXISTS `user_order_rel`;
CREATE TABLE `user_order_rel`  (
  `id` int(128) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime NULL DEFAULT NULL,
  `gmt_modified` datetime NULL DEFAULT NULL,
  `order_id` int(128) NULL DEFAULT NULL,
  `user_id` int(128) NULL DEFAULT NULL,
  `leader` tinyint(4) NOT NULL COMMENT '0队员1队长',
  `deleted` tinyint(2) NULL DEFAULT 0 COMMENT '0存在1不存在',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
