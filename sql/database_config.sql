/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80043 (8.0.43)
 Source Host           : localhost:3306
 Source Schema         : code_generator

 Target Server Type    : MySQL
 Target Server Version : 80043 (8.0.43)
 File Encoding         : 65001

 Date: 24/09/2025 15:43:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for database_config
-- ----------------------------
DROP TABLE IF EXISTS `database_config`;
CREATE TABLE `database_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `config_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置名称',
  `database_type` enum('MYSQL','POSTGRESQL') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据库类型，MYSQL或POSTGRESQL',
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主机地址',
  `port` int NOT NULL COMMENT '端口号',
  `database_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据库名称',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建者标识（V1版本不涉及，可按需删除）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间（V1版本不涉及，可按需删除）',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间（V1版本不涉及，可按需删除）',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志，0表示未删除，1表示删除，（V1版本不涉及，可按需删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `config_name_unique`(`config_name` ASC) USING BTREE COMMENT '唯一配置名'
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用于存储页面中数据库的相关配置，方便后续直接连接相关配置数据库，而不用重复连接' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
