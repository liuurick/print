/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 100137
 Source Host           : localhost:3306
 Source Schema         : print

 Target Server Type    : MySQL
 Target Server Version : 100137
 File Encoding         : 65001

 Date: 13/10/2019 12:47:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bonus_info
-- ----------------------------
DROP TABLE IF EXISTS `bonus_info`;
CREATE TABLE `bonus_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` int(11) NOT NULL,
  `threshold` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '阈值 30张 可以打折，也就是。黑白一张的价格*bonus',
  `bonus` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '0.8 ',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '如：满三十张打八折 ，每一个店铺创建的时候都会生成一个bonus记录，即使他没有bonus信息，也会写入这个店铺没有优惠信息这条记录。',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bonus_info
-- ----------------------------
INSERT INTO `bonus_info` VALUES (1, 1, '30', '0.8', '如果打印的张数超过 30 张，按照每张原价的价格 * 0.8.');
INSERT INTO `bonus_info` VALUES (2, 2, '40', '0.9', '如果打印的张数超过 40 张，按照每张原价的价格 * 0.9');
INSERT INTO `bonus_info` VALUES (3, 3, '35', '0.8', '如果打印的张数超过 35 张，按照每张原价的价格 * 0.8');
INSERT INTO `bonus_info` VALUES (4, 4, '45', '0.7', '如果打印的张数超过 45 张，按照每张原价的价格 * 0.7');
INSERT INTO `bonus_info` VALUES (5, 5, NULL, NULL, '此店面没有优惠活动！');

-- ----------------------------
-- Table structure for color_info
-- ----------------------------
DROP TABLE IF EXISTS `color_info`;
CREATE TABLE `color_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `color_type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '0 黑白 1 彩色',
  `shop_id` int(11) DEFAULT NULL,
  `price` decimal(20, 2) DEFAULT NULL COMMENT '这个是“起价”，店家录入的时候一张最基本的多少钱就写入这个字段。而不再写入到单页、A4的字段。如果黑白一张是 1.1 元。而单页、A4都为1。这个是系数，1.1 * 1。',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of color_info
-- ----------------------------
INSERT INTO `color_info` VALUES (1, '0', 1, 1.00);
INSERT INTO `color_info` VALUES (2, '1', 1, 2.00);
INSERT INTO `color_info` VALUES (3, '0', 2, 1.00);
INSERT INTO `color_info` VALUES (5, '0', 3, 1.00);
INSERT INTO `color_info` VALUES (6, '1', 3, 1.90);
INSERT INTO `color_info` VALUES (7, '0', 4, 0.50);
INSERT INTO `color_info` VALUES (8, '1', 4, 1.70);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `target_id` int(11) NOT NULL,
  `ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` int(11) DEFAULT 0,
  `status` int(4) DEFAULT 0,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `comment_type` int(4) NOT NULL COMMENT '0 店铺 1 分享',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 1, 9, '', '希望大家踊跃下载~', 0, 0, '2019-06-14 20:22:09', '2019-06-14 20:22:09', 0);
INSERT INTO `comment` VALUES (2, 1, 7, '', '非常不错的资料~~~', 0, 0, '2019-06-14 20:54:47', '2019-06-14 20:54:47', 0);

-- ----------------------------
-- Table structure for credit_history
-- ----------------------------
DROP TABLE IF EXISTS `credit_history`;
CREATE TABLE `credit_history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `score` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '3' COMMENT '默认三分',
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  `file_spec` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件位置',
  `page_num` int(4) DEFAULT NULL COMMENT '页数',
  `share` int(4) NOT NULL DEFAULT 0 COMMENT ' 0 私有 1 公开',
  `integral` int(4) DEFAULT NULL COMMENT '积分 1->10 ',
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `new_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 110 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES (27, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-07 20:47:06', '2019-04-07 20:47:06', 'b2f2c436-9563-4a37-b812-9b0aee5d6210.pdf');
INSERT INTO `file` VALUES (28, '浪潮优派-本科生毕业论文工作指南.doc', 1, NULL, 9, 1, 5, NULL, '2019-04-07 20:51:22', '2019-04-07 20:51:22', '99401650-6d06-4113-870c-8c224569a1a4.doc');
INSERT INTO `file` VALUES (29, '浪潮优派-本科生毕业论文工作指南.doc', 1, NULL, 9, 0, NULL, NULL, '2019-04-07 21:07:11', '2019-04-07 21:07:11', '22911727-0723-45d9-879c-97281e8e5d36.doc');
INSERT INTO `file` VALUES (30, '浪潮优派-本科生毕业论文工作指南.doc', 1, NULL, 9, 0, NULL, NULL, '2019-04-07 21:11:40', '2019-04-07 21:11:40', 'd33450f8-adbc-48e0-b45e-f3ac00d34ebf.doc');
INSERT INTO `file` VALUES (31, '浪潮优派-本科生毕业论文工作指南.doc', 1, NULL, 9, 0, NULL, NULL, '2019-04-07 23:53:47', '2019-04-07 23:53:47', 'a4f26a44-1539-48c2-9d2a-30f4870db953.doc');
INSERT INTO `file` VALUES (32, '浪潮优派-本科生毕业论文工作指南.doc', 1, NULL, 9, 0, NULL, NULL, '2019-04-07 23:56:28', '2019-04-07 23:56:28', 'dbd3dd77-1eba-4ba3-90e3-8918e76c2937.doc');
INSERT INTO `file` VALUES (33, '浪潮优派-本科生毕业论文工作指南.doc', 1, NULL, 9, 1, 3, NULL, '2019-04-07 23:58:16', '2019-04-27 17:43:00', '0f78e92f-4252-465f-9a91-18743c6420bd.doc');
INSERT INTO `file` VALUES (34, '浪潮优派-本科生毕业论文工作指南.doc', 1, NULL, 9, 0, NULL, NULL, '2019-04-08 00:01:05', '2019-04-08 00:01:05', 'e8554350-8ef7-4b49-9e20-667bfb6b023b.doc');
INSERT INTO `file` VALUES (35, '浪潮优派-本科生毕业论文工作指南.doc', 1, NULL, 9, 0, NULL, NULL, '2019-04-08 00:15:24', '2019-04-08 00:15:24', '3ae07b4c-8345-4ebc-9f32-37b716920801.doc');
INSERT INTO `file` VALUES (36, '浪潮优派-本科生毕业论文工作指南.doc', 1, NULL, 9, 0, NULL, NULL, '2019-04-08 00:15:50', '2019-04-08 00:15:50', 'c8d1be3c-097b-4275-83b7-ab6717301938.doc');
INSERT INTO `file` VALUES (37, '浪潮优派-本科生毕业论文工作指南.doc', 1, NULL, 9, 0, NULL, NULL, '2019-04-08 00:23:53', '2019-04-08 00:23:53', 'a36b4dda-c4a4-4dd8-b9c6-0dc7713a59d4.doc');
INSERT INTO `file` VALUES (38, '浪潮优派-本科生毕业论文工作指南.doc', 1, NULL, 9, 0, NULL, NULL, '2019-04-08 00:39:18', '2019-04-08 00:39:18', '9c6d4829-f242-4868-867c-654a404c7009.doc');
INSERT INTO `file` VALUES (39, '浪潮优派-本科生毕业论文工作指南.doc', 1, NULL, 9, 0, NULL, NULL, '2019-04-08 00:43:07', '2019-04-08 00:43:07', '09226b72-e8d5-434f-a4f7-f28202d2f3b9.doc');
INSERT INTO `file` VALUES (40, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 13:28:04', '2019-04-08 13:28:04', 'af7c4f98-3483-4352-9cb9-15f90f0667da.pdf');
INSERT INTO `file` VALUES (41, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 13:31:31', '2019-04-08 13:31:31', '456e55ff-d931-4f0e-8e6a-3ad48a2dad15.pdf');
INSERT INTO `file` VALUES (42, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 13:33:21', '2019-04-08 13:33:21', 'da22af18-acc4-4998-a896-bbf5815339e5.pdf');
INSERT INTO `file` VALUES (43, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 13:37:02', '2019-04-08 13:37:02', '6a2da326-06b6-4680-829f-c20a892563db.pdf');
INSERT INTO `file` VALUES (44, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 19:21:15', '2019-04-08 19:21:15', '7f0c60a5-efe2-49b2-893d-0c69ef1d0c3a.pdf');
INSERT INTO `file` VALUES (45, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 19:51:20', '2019-04-08 19:51:20', '66bdfeaf-070e-45e9-aba0-756bb223b999.pdf');
INSERT INTO `file` VALUES (46, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 19:55:10', '2019-04-08 19:55:10', '028337eb-84e4-4dcd-8aa1-5d400afd91f3.pdf');
INSERT INTO `file` VALUES (47, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 20:09:40', '2019-04-08 20:09:40', '1ee486c0-8689-41e9-b13c-6a91cba6cf3d.pdf');
INSERT INTO `file` VALUES (48, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 20:15:43', '2019-04-08 20:15:43', '061db8b5-6aa3-4774-afa5-a44c9b21b6f0.pdf');
INSERT INTO `file` VALUES (49, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 20:17:34', '2019-04-08 20:17:34', '4eb959da-50b4-4c80-806a-6d41ca7ce869.pdf');
INSERT INTO `file` VALUES (50, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 21:11:31', '2019-04-08 21:11:31', 'e2f46ee5-22e0-4b0b-9110-c5ce6a392260.pdf');
INSERT INTO `file` VALUES (51, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 21:16:06', '2019-04-08 21:16:06', 'd992fa72-23d0-4156-a53d-d729ca0f7403.pdf');
INSERT INTO `file` VALUES (52, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 21:19:34', '2019-04-08 21:19:34', '9b677c5c-c126-44ab-a0f5-7330fd51a7dc.pdf');
INSERT INTO `file` VALUES (53, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 21:23:55', '2019-04-08 21:23:55', 'c9cdbfa8-0190-46aa-951a-506dfc2f9cbe.pdf');
INSERT INTO `file` VALUES (54, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-08 21:29:51', '2019-04-08 21:29:51', 'd56cee8d-9b6c-4938-890d-2462ff264f94.pdf');
INSERT INTO `file` VALUES (55, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-09 00:09:25', '2019-04-09 00:09:25', '3d8ec171-b974-4460-ae14-9de29f67e06d.pdf');
INSERT INTO `file` VALUES (56, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-09 00:13:43', '2019-04-09 00:13:43', 'fdd0ffce-d59c-4467-a0fc-b80b247d8a38.pdf');
INSERT INTO `file` VALUES (57, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-09 01:03:34', '2019-04-09 01:03:34', '8929633d-ccc1-4e19-a51a-8fa2b0b25f7c.pdf');
INSERT INTO `file` VALUES (58, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-09 01:07:02', '2019-04-09 01:07:02', 'ebe08b28-36bd-4b93-ae4f-49deecd17133.pdf');
INSERT INTO `file` VALUES (59, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-09 01:08:15', '2019-04-09 01:08:15', '7ce72d13-0dad-495a-8ce4-e99cc93523d5.pdf');
INSERT INTO `file` VALUES (60, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-09 01:10:44', '2019-04-09 01:10:44', '4ce0cc2b-7b27-4873-b80e-251f7cfe8384.pdf');
INSERT INTO `file` VALUES (61, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-09 01:12:03', '2019-04-09 01:12:03', '44b65467-1060-4619-a679-5cc7d0592663.pdf');
INSERT INTO `file` VALUES (62, 'usingthymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-09 01:19:29', '2019-04-09 01:19:29', 'ff2ab8a7-0e83-4bf9-b0c6-144094e3d837.pdf');
INSERT INTO `file` VALUES (63, 'SpringBoot实战(第4版)清晰版@www.java1234.com.pdf', 6, NULL, 225, 0, NULL, NULL, '2019-04-09 01:27:32', '2019-04-09 01:27:32', '0227c53f-dd74-4f61-ae65-7b339b8edfe7.pdf');
INSERT INTO `file` VALUES (64, 'thymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-09 13:19:43', '2019-04-09 13:19:43', 'cd83b336-84d3-4899-88bb-32d7645b66ee.pdf');
INSERT INTO `file` VALUES (65, 'thymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-09 13:38:13', '2019-04-09 13:38:13', '97a5f358-3b8e-42b5-b2ed-a9187746188b.pdf');
INSERT INTO `file` VALUES (66, 'thymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-10 13:41:05', '2019-04-10 13:41:05', '329c6a95-25c9-4fb0-88b9-b918ac4e0f96.pdf');
INSERT INTO `file` VALUES (67, 'thymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-10 13:45:07', '2019-04-10 13:45:07', '97f91af5-f7a3-4533-b34f-6d414b252702.pdf');
INSERT INTO `file` VALUES (68, 'SpringBoot实战(第4版)清晰版.pdf', 6, NULL, 225, 0, NULL, NULL, '2019-04-10 14:08:41', '2019-04-10 14:08:41', '271f775a-1cc7-483e-a76e-17a7ba26a5bf.pdf');
INSERT INTO `file` VALUES (69, 'thymeleaf.pdf', 6, NULL, 106, 0, NULL, NULL, '2019-04-10 14:23:35', '2019-04-10 14:23:35', '140545f9-6a22-4784-a34f-fed467c52f92.pdf');
INSERT INTO `file` VALUES (70, 'thymeleaf.pdf', 6, NULL, 106, 0, NULL, NULL, '2019-04-10 14:32:18', '2019-04-10 14:32:18', '03f04e16-6364-49b4-92bb-8de39f86955a.pdf');
INSERT INTO `file` VALUES (71, 'thymeleaf.pdf', 6, NULL, 106, 0, NULL, NULL, '2019-04-10 14:44:14', '2019-04-10 14:44:14', 'a14d8102-194c-4e76-9fa8-f1ff13b31286.pdf');
INSERT INTO `file` VALUES (72, 'thymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-10 14:49:30', '2019-04-10 14:49:30', 'e7bed4b3-fa34-421c-bbec-8ad92d115a4d.pdf');
INSERT INTO `file` VALUES (73, 'thymeleaf.pdf', 6, NULL, 106, 0, NULL, NULL, '2019-04-10 14:54:36', '2019-04-10 14:54:36', '87ddd929-459f-49a2-9e9a-c4e9323d1461.pdf');
INSERT INTO `file` VALUES (74, 'thymeleaf.pdf', 6, NULL, 106, 0, NULL, NULL, '2019-04-10 15:00:05', '2019-04-10 15:00:05', '75310541-ab09-4444-b753-8e3771e465b4.pdf');
INSERT INTO `file` VALUES (75, 'thymeleaf.pdf', 1, NULL, 106, 0, NULL, NULL, '2019-04-10 15:07:23', '2019-04-10 15:07:23', '5c7a2e23-91e0-407a-aee3-b3a81f17a304.pdf');
INSERT INTO `file` VALUES (76, 'thymeleaf.pdf', 6, NULL, 106, 1, 9, NULL, '2019-04-10 15:12:37', '2019-04-23 12:44:34', '397c8ffc-39f2-4b61-bdc4-d97de5e2334b.pdf');
INSERT INTO `file` VALUES (77, 'thymeleaf.pdf', 1, NULL, 106, 1, 9, NULL, '2019-04-10 15:56:02', '2019-05-02 13:22:42', '28975455-b193-4549-8cf8-f571b71d08cd.pdf');
INSERT INTO `file` VALUES (78, '666.pdf', 1, NULL, 9, 1, 3, NULL, '2019-04-17 01:43:02', '2019-04-22 17:21:39', '3e98a96b-629f-41f9-b57a-12846110776d.pdf');
INSERT INTO `file` VALUES (79, '05_设计任务书.doc', 6, NULL, 1, 0, NULL, NULL, '2019-04-17 12:48:22', '2019-04-17 12:48:22', '381d416f-7c82-4cff-9945-b9abde09216f.doc');
INSERT INTO `file` VALUES (80, '05_设计任务书.doc', 4, NULL, 1, 0, NULL, NULL, '2019-04-17 13:01:41', '2019-04-17 13:01:41', 'e1773714-1cf4-495a-a986-dec40a157aba.doc');
INSERT INTO `file` VALUES (81, '05_设计任务书.doc', 4, NULL, 4, 0, NULL, NULL, '2019-04-17 13:02:00', '2019-04-17 13:02:00', '6c7bde80-217e-454e-a8ca-6d48ac35fdb7.doc');
INSERT INTO `file` VALUES (82, '05_设计任务书.doc', 4, NULL, 4, 0, NULL, NULL, '2019-04-17 13:06:42', '2019-04-17 13:06:42', '1680a568-07a1-4b39-81ee-cd03b831b9df.doc');
INSERT INTO `file` VALUES (83, '05_设计任务书.doc', 6, NULL, 4, 0, NULL, NULL, '2019-04-17 13:19:50', '2019-04-17 13:19:50', 'b841ed18-1a87-4c60-b2d1-ce2153dd30d6.doc');
INSERT INTO `file` VALUES (84, '05_设计任务书.doc', 6, NULL, 4, 0, NULL, NULL, '2019-04-18 11:54:44', '2019-04-18 11:54:44', '1cbce1a7-8462-4edd-a842-097fadb0dcb8.doc');
INSERT INTO `file` VALUES (85, '05_设计任务书.doc', 1, NULL, 1, 0, NULL, NULL, '2019-04-18 11:59:15', '2019-04-18 11:59:15', '1f6e5c0c-8e0b-48fe-a89c-8190a592e072.doc');
INSERT INTO `file` VALUES (86, '05_设计任务书.doc', 6, NULL, 1, 0, NULL, NULL, '2019-04-18 12:01:42', '2019-04-18 12:01:42', 'b48e63d3-4588-4865-9af0-541905d7f932.doc');
INSERT INTO `file` VALUES (87, '05_设计任务书.doc', 6, NULL, 1, 0, NULL, NULL, '2019-04-18 12:05:11', '2019-04-18 12:05:11', '7a05a5e5-ea47-4817-84d3-173cf94af59e.doc');
INSERT INTO `file` VALUES (88, '02 毕业论文（设计）正文－编排模版参考格式(理工科类)2010.doc', 6, NULL, 1, 1, 1, NULL, '2019-04-18 12:11:30', '2019-05-14 16:57:26', 'b55f6b58-8d1c-48c8-b2ce-10c1906e9abf.doc');
INSERT INTO `file` VALUES (89, '毕业论文.doc', 1, NULL, 10, 1, 5, NULL, '2019-04-29 21:06:04', '2019-04-29 21:06:08', '88a365a4-73ef-446b-95cf-6e894ebe2da0.doc');
INSERT INTO `file` VALUES (90, '666.pdf', 1, NULL, 9, 1, 3, NULL, '2019-05-14 16:50:49', '2019-05-30 00:40:30', 'd19c179b-5ec3-424b-ae1d-0c316e6660a1.pdf');
INSERT INTO `file` VALUES (91, '666.pdf', 1, NULL, 9, 0, NULL, NULL, '2019-05-14 17:07:40', '2019-05-14 17:07:40', 'de21cea6-f355-4baa-b742-75584093eacb.pdf');
INSERT INTO `file` VALUES (92, '毕业论文.doc', 1, NULL, 10, 0, NULL, NULL, '2019-05-24 18:01:56', '2019-05-24 18:01:56', '1cc04acb-1945-43f9-98cf-87adb0382685.doc');
INSERT INTO `file` VALUES (93, '文档.pdf', 1, NULL, 9, 0, NULL, NULL, '2019-05-24 18:09:13', '2019-05-24 18:09:13', '922f91be-7423-4d18-b631-2fef88eafacb.pdf');
INSERT INTO `file` VALUES (94, '毕业论文.doc', 1, NULL, 10, 0, NULL, NULL, '2019-05-24 18:09:29', '2019-05-24 18:09:29', '0a18a52e-8712-40be-8f9a-d5d0b572ad74.doc');
INSERT INTO `file` VALUES (95, '文档.pdf', 1, NULL, 9, 1, 3, NULL, '2019-05-24 19:13:35', '2019-05-24 19:14:02', '1046b235-ec3a-4396-bea3-8b5deaf4837a.pdf');
INSERT INTO `file` VALUES (96, '文档.pdf', 6, NULL, 9, 0, NULL, NULL, '2019-05-24 19:49:45', '2019-05-24 19:49:45', '9eda92fa-a1df-49d3-a01b-0ed2b27790bb.pdf');
INSERT INTO `file` VALUES (97, '文档.pdf', 6, NULL, 9, 1, 3, NULL, '2019-05-24 20:54:16', '2019-06-15 09:07:10', '2d614d60-5442-48e6-b6f8-7db0b33ce7dc.pdf');
INSERT INTO `file` VALUES (98, '毕业论文.doc', 6, NULL, 10, 0, NULL, NULL, '2019-05-29 21:11:01', '2019-05-29 21:11:01', 'e7af93bf-517b-4aac-aaf4-7e6d4503e0b5.doc');
INSERT INTO `file` VALUES (99, '文档.pdf', 1, NULL, 9, 0, NULL, NULL, '2019-06-14 16:08:38', '2019-06-14 16:08:38', '3798c0e0-5baa-4e66-800f-ad0e8641d1e8.pdf');
INSERT INTO `file` VALUES (100, '文档.pdf', 1, NULL, 9, 0, NULL, NULL, '2019-06-14 21:52:55', '2019-06-14 21:52:55', '5c872967-6ffa-401c-8c77-f7dff645d605.pdf');
INSERT INTO `file` VALUES (101, '文档.pdf', 1, NULL, 9, 0, NULL, NULL, '2019-06-15 00:07:38', '2019-06-15 00:07:38', 'c408a11b-9fb4-40a8-86f1-0e9d290c2dfa.pdf');
INSERT INTO `file` VALUES (102, '文档.pdf', 1, NULL, 9, 0, NULL, NULL, '2019-06-15 08:44:19', '2019-06-15 08:44:19', '9450aca6-3c0a-4e31-992c-467fc9c6f082.pdf');
INSERT INTO `file` VALUES (103, '文档.pdf', 1, NULL, 9, 0, NULL, NULL, '2019-06-15 09:12:58', '2019-06-15 09:12:58', '79dd234f-f21c-4f23-94c4-ab694ce849ff.pdf');
INSERT INTO `file` VALUES (104, '机器学习.pdf', 1, NULL, 1, 1, 1, NULL, '2019-10-05 20:25:19', '2019-10-05 20:35:47', 'a2ce1486-9f20-4983-9660-1da9af498511.pdf');
INSERT INTO `file` VALUES (105, '机器学习.pdf', 1, NULL, 1, 0, NULL, NULL, '2019-10-05 20:46:12', '2019-10-05 20:46:12', '66ab9f31-55c2-4c26-b1f2-a753aecbe056.pdf');
INSERT INTO `file` VALUES (106, '机器学习.pdf', 1, NULL, 1, 0, NULL, NULL, '2019-10-05 21:11:13', '2019-10-05 21:11:13', '09da6f1c-69d7-4200-aaee-f5729798f5f8.pdf');
INSERT INTO `file` VALUES (107, '机器学习.pdf', 1, NULL, 1, 0, NULL, NULL, '2019-10-05 21:28:09', '2019-10-05 21:28:09', '2709ba8a-1361-4bf4-b54c-78c49dfea147.pdf');
INSERT INTO `file` VALUES (108, '机器学习.pdf', 1, NULL, 1, 0, NULL, NULL, '2019-10-06 06:32:59', '2019-10-06 06:32:59', '4b63e261-8bdd-4ef5-b158-0da0582c6613.pdf');
INSERT INTO `file` VALUES (109, '机器学习.pdf', 1, NULL, 1, 0, NULL, NULL, '2019-10-06 06:40:31', '2019-10-06 06:40:31', 'd4ea0b38-844d-414d-a710-c6124980c219.pdf');

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  `file_id` int(11) NOT NULL,
  `file_quantity` int(4) NOT NULL COMMENT '打印份数',
  `current_price` decimal(10, 2) NOT NULL COMMENT '下单时的价格',
  `size_info_type` int(32) DEFAULT 4 COMMENT '对应 size_info 表中的字段，此订单文件是 A5还是A6',
  `user_des` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `color_info_type` int(11) DEFAULT NULL COMMENT '黑白彩色规格',
  `page_info_type` int(11) DEFAULT NULL COMMENT '单双面规格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES (2, '1554743018561458287', 1, 57, 1, 95.40, 4, NULL, '2019-04-09 01:03:38', '2019-04-09 01:03:38', 0, 0);
INSERT INTO `order_item` VALUES (6, '1554743529445304665', 1, 61, 1, 63.60, 4, NULL, '2019-04-09 01:12:09', '2019-04-09 01:12:09', 0, 1);
INSERT INTO `order_item` VALUES (7, '1554743973003913197', 1, 62, 1, 84.80, 4, NULL, '2019-04-09 01:19:33', '2019-04-09 01:19:33', 0, 0);
INSERT INTO `order_item` VALUES (8, '1554744458053269357', 6, 63, 1, 78.75, 4, NULL, '2019-04-09 01:27:38', '2019-04-09 01:27:38', 0, 0);
INSERT INTO `order_item` VALUES (9, '1554787186070936219', 1, 64, 1, 66.78, 4, NULL, '2019-04-09 13:19:46', '2019-04-09 13:19:46', 0, 1);
INSERT INTO `order_item` VALUES (10, '1554788296108371016', 1, 65, 1, 66.78, 4, NULL, '2019-04-09 13:38:16', '2019-04-09 13:38:16', 0, 1);
INSERT INTO `order_item` VALUES (11, '1554875112256806964', 1, 67, 1, 95.40, 4, NULL, '2019-04-10 13:45:12', '2019-04-10 13:45:12', 0, 0);
INSERT INTO `order_item` VALUES (12, '1554876704909305579', 6, 68, 1, 135.00, 4, NULL, '2019-04-10 14:11:44', '2019-04-10 14:11:44', 0, 1);
INSERT INTO `order_item` VALUES (13, '1554877418228824170', 6, 69, 1, 67.84, 4, NULL, '2019-04-10 14:23:38', '2019-04-10 14:23:38', 0, 1);
INSERT INTO `order_item` VALUES (14, '1554877941134453482', 6, 70, 1, 67.84, 4, NULL, '2019-04-10 14:32:21', '2019-04-10 14:32:21', 0, 1);
INSERT INTO `order_item` VALUES (15, '1554878659199238945', 6, 71, 1, 128.90, 4, NULL, '2019-04-10 14:44:19', '2019-04-10 14:44:19', 1, 1);
INSERT INTO `order_item` VALUES (16, '1554878972849361181', 1, 72, 1, 67.84, 4, NULL, '2019-04-10 14:49:32', '2019-04-10 14:49:32', 0, 1);
INSERT INTO `order_item` VALUES (17, '1554879278151669912', 6, 73, 1, 128.90, 4, NULL, '2019-04-10 14:54:38', '2019-04-10 14:54:38', 1, 1);
INSERT INTO `order_item` VALUES (18, '1554879611576239040', 6, 74, 1, 66.78, 4, NULL, '2019-04-10 15:00:11', '2019-04-10 15:00:11', 0, 1);
INSERT INTO `order_item` VALUES (19, '1554880048766231938', 1, 75, 1, 37.10, 4, NULL, '2019-04-10 15:07:28', '2019-04-10 15:07:28', 0, 0);
INSERT INTO `order_item` VALUES (20, '1554880361730201616', 6, 76, 1, 126.14, 4, NULL, '2019-04-10 15:12:41', '2019-04-10 15:12:41', 1, 0);
INSERT INTO `order_item` VALUES (21, '1554882965254163093', 1, 77, 1, 161.12, 4, NULL, '2019-04-10 15:56:05', '2019-04-10 15:56:05', 1, 0);
INSERT INTO `order_item` VALUES (22, '1555436602218577272', 1, 78, 1, 6.30, 4, NULL, '2019-04-17 01:43:22', '2019-04-17 01:43:22', 0, 1);
INSERT INTO `order_item` VALUES (23, '1555476511238875435', 6, 79, 2, 3.04, 4, NULL, '2019-04-17 12:48:31', '2019-04-17 12:48:31', 1, 1);
INSERT INTO `order_item` VALUES (24, '1555477329692141351', 4, 81, 1, 6.08, 4, NULL, '2019-04-17 13:02:09', '2019-04-17 13:02:09', 1, 1);
INSERT INTO `order_item` VALUES (25, '1555477606410286280', 4, 82, 1, 3.20, 4, NULL, '2019-04-17 13:06:46', '2019-04-17 13:06:46', 0, 1);
INSERT INTO `order_item` VALUES (26, '1555478392829527145', 6, 83, 1, 2.80, 4, NULL, '2019-04-17 13:19:52', '2019-04-17 13:19:52', 0, 1);
INSERT INTO `order_item` VALUES (27, '1555559700938323783', 6, 84, 1, 3.20, 4, NULL, '2019-04-18 11:55:00', '2019-04-18 11:55:00', 0, 1);
INSERT INTO `order_item` VALUES (28, '1555559958292844690', 1, 85, 1, 0.75, 4, NULL, '2019-04-18 11:59:18', '2019-04-18 11:59:18', 0, 1);
INSERT INTO `order_item` VALUES (29, '1555560104991785771', 6, 86, 1, 0.80, 4, NULL, '2019-04-18 12:01:45', '2019-04-18 12:01:45', 0, 1);
INSERT INTO `order_item` VALUES (30, '1555560314558531602', 6, 87, 1, 0.75, 4, NULL, '2019-04-18 12:05:14', '2019-04-18 12:05:14', 0, 1);
INSERT INTO `order_item` VALUES (31, '1555560693844820038', 6, 88, 1, 0.75, 4, NULL, '2019-04-18 12:11:33', '2019-04-18 12:11:33', 0, 1);
INSERT INTO `order_item` VALUES (32, '1557823867212421216', 1, 90, 1, 9.00, 4, NULL, '2019-05-14 16:51:07', '2019-05-14 16:51:07', 0, 0);
INSERT INTO `order_item` VALUES (33, '1557824906196370861', 1, 91, 2, 18.90, 3, NULL, '2019-05-14 17:08:26', '2019-05-14 17:08:26', 0, 1);
INSERT INTO `order_item` VALUES (34, '1558692581456860403', 1, 94, 1, 10.00, 4, NULL, '2019-05-24 18:09:41', '2019-05-24 18:09:41', 0, 0);
INSERT INTO `order_item` VALUES (35, '1558702459139207905', 6, 97, 1, 9.00, 4, NULL, '2019-05-24 20:54:19', '2019-05-24 20:54:19', 0, 0);
INSERT INTO `order_item` VALUES (36, '1560499729207430719', 1, 99, 2, 12.60, 4, NULL, '2019-06-14 16:08:49', '2019-06-14 16:08:49', 0, 1);
INSERT INTO `order_item` VALUES (37, '1560520393636753831', 1, 100, 1, 9.00, 4, NULL, '2019-06-14 21:53:13', '2019-06-14 21:53:13', 0, 0);
INSERT INTO `order_item` VALUES (38, '1560528546108167873', 1, 101, 2, 18.00, 4, NULL, '2019-06-15 00:09:06', '2019-06-15 00:09:06', 0, 0);
INSERT INTO `order_item` VALUES (39, '1560559466991379053', 1, 102, 1, 9.00, 2, NULL, '2019-06-15 08:44:26', '2019-06-15 08:44:26', 0, 0);
INSERT INTO `order_item` VALUES (40, '1560561217445858169', 1, 103, 1, 11.70, 3, NULL, '2019-06-15 09:13:37', '2019-06-15 09:13:37', 0, 0);
INSERT INTO `order_item` VALUES (41, '1570278360445743493', 1, 104, 1, 1.00, 4, NULL, '2019-10-05 20:26:00', '2019-10-05 20:26:00', 0, 0);
INSERT INTO `order_item` VALUES (42, '1570279581467164764', 1, 105, 2, 2.00, 4, NULL, '2019-10-05 20:46:21', '2019-10-05 20:46:21', 0, 0);
INSERT INTO `order_item` VALUES (43, '1570281125890357315', 1, 106, 2, 1.40, 4, NULL, '2019-10-05 21:12:05', '2019-10-05 21:12:05', 0, 1);
INSERT INTO `order_item` VALUES (44, '1570282146039583037', 1, 107, 2, 2.00, 4, NULL, '2019-10-05 21:29:06', '2019-10-05 21:29:06', 0, 0);
INSERT INTO `order_item` VALUES (45, '1570314782760650869', 1, 108, 1, 0.70, 4, NULL, '2019-10-06 06:33:02', '2019-10-06 06:33:02', 0, 1);
INSERT INTO `order_item` VALUES (46, '1570315270152699942', 1, 109, 1, 1.00, 4, NULL, '2019-10-06 06:41:10', '2019-10-06 06:41:10', 0, 0);

-- ----------------------------
-- Table structure for order_master
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master`  (
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shop_id` int(11) NOT NULL,
  `buyer_id` int(11) NOT NULL,
  `buyer_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `buyer_phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `buyer_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `payment` decimal(20, 2) NOT NULL,
  `payment_type` int(4) NOT NULL DEFAULT 0 COMMENT '0 支付宝  1 微信支付',
  `order_status` int(4) NOT NULL DEFAULT 0 COMMENT '订单状态, 默认为新下单 还有取消，完结',
  `payment_time` datetime(0) DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `refuse_reason` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拒绝理由',
  `get_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '取货码',
  PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of order_master
-- ----------------------------
INSERT INTO `order_master` VALUES ('1554743529445304665', 1, 1, 'jack', '178541752659', '1027700603@qq.com', 63.60, 0, 10, '0000-00-00 00:00:00', '2019-04-09 01:12:09', '2019-04-09 01:12:09', NULL, NULL);
INSERT INTO `order_master` VALUES ('1554743973003913197', 3, 1, 'jack', '178541752659', '1027700603@qq.com', 84.80, 0, 10, '0000-00-00 00:00:00', '2019-04-09 01:19:33', '2019-04-09 01:19:33', NULL, NULL);
INSERT INTO `order_master` VALUES ('1554744458053269357', 4, 6, 'qian', '15659548846', '1565998115@qq.com', 78.75, 0, 10, '0000-00-00 00:00:00', '2019-04-09 01:27:38', '2019-04-09 01:27:38', NULL, NULL);
INSERT INTO `order_master` VALUES ('1554787186070936219', 2, 1, 'jack', '178541752659', '1027700603@qq.com', 66.78, 0, 10, '0000-00-00 00:00:00', '2019-04-09 13:19:46', '2019-04-09 13:19:46', NULL, NULL);
INSERT INTO `order_master` VALUES ('1554788296108371016', 2, 1, 'jack', '178541752659', '1027700603@qq.com', 66.78, 0, 10, '0000-00-00 00:00:00', '2019-04-09 13:38:16', '2019-04-09 13:38:16', NULL, NULL);
INSERT INTO `order_master` VALUES ('1554875112256806964', 2, 1, 'jack', '178541752659', '1027700603@qq.com', 95.40, 0, 10, NULL, '2019-04-10 13:45:12', '2019-04-10 13:45:12', NULL, NULL);
INSERT INTO `order_master` VALUES ('1554876704909305579', 1, 6, 'qian', '15659548846', '1565998115@qq.com', 135.00, 0, 10, NULL, '2019-04-10 14:11:44', '2019-04-10 14:11:44', NULL, NULL);
INSERT INTO `order_master` VALUES ('1554877418228824170', 3, 6, 'qian', '15659548846', '1565998115@qq.com', 67.84, 0, 10, NULL, '2019-04-10 14:23:38', '2019-04-10 14:23:38', NULL, NULL);
INSERT INTO `order_master` VALUES ('1554877941134453482', 3, 6, 'qian', '17854176745', '1565998115@qq.com', 67.84, 0, 40, '2019-04-10 14:33:12', '2019-04-10 14:32:21', '2019-04-10 14:33:12', NULL, '521-521');
INSERT INTO `order_master` VALUES ('1554878659199238945', 3, 6, 'qian', '17854176692', '1565998115@qq.com', 128.90, 0, 40, '2019-04-10 14:45:02', '2019-04-10 14:44:19', '2019-04-10 14:45:02', NULL, '55-66-959');
INSERT INTO `order_master` VALUES ('1554878972849361181', 3, 1, 'jack', '17854176692', '1027700603@qq.com', 67.84, 0, 60, '2019-04-10 14:49:55', '2019-04-10 14:49:32', '2019-05-26 17:36:53', NULL, '66-66-666');
INSERT INTO `order_master` VALUES ('1554879278151669912', 3, 6, 'qian', '17854176692', '1565998115@qq.com', 128.90, 0, 60, '2019-04-10 14:55:27', '2019-04-10 14:54:38', '2019-05-26 17:57:39', NULL, '56-59-515');
INSERT INTO `order_master` VALUES ('1554879611576239040', 2, 6, 'qian', '15659548846', '1565998115@qq.com', 66.78, 0, 20, '2019-04-10 15:00:52', '2019-04-10 15:00:11', '2019-04-10 15:00:53', NULL, NULL);
INSERT INTO `order_master` VALUES ('1554880048766231938', 4, 1, 'jack', '178541752659', '1027700603@qq.com', 37.10, 0, 10, NULL, '2019-04-10 15:07:28', '2019-04-10 15:07:28', NULL, NULL);
INSERT INTO `order_master` VALUES ('1554880361730201616', 4, 6, 'qian', '15659548846', '1565998115@qq.com', 126.14, 0, 10, NULL, '2019-04-10 15:12:41', '2019-04-10 15:12:41', NULL, NULL);
INSERT INTO `order_master` VALUES ('1554882965254163093', 3, 1, 'jack', '178541752659', '1027700603@qq.com', 161.12, 0, 20, '2019-04-10 15:56:27', '2019-04-10 15:56:05', '2019-04-10 15:56:27', NULL, NULL);
INSERT INTO `order_master` VALUES ('1555436602218577272', 2, 1, 'jack', '178541752659', '1027700603@qq.com', 6.30, 0, 10, NULL, '2019-04-17 01:43:22', '2019-04-17 01:43:22', NULL, NULL);
INSERT INTO `order_master` VALUES ('1555476511238875435', 3, 6, 'qian', '15659548846', '1565998115@qq.com', 3.04, 0, 10, NULL, '2019-04-17 12:48:31', '2019-04-17 12:48:31', NULL, NULL);
INSERT INTO `order_master` VALUES ('1555478392829527145', 2, 6, 'qian', '15659548846', '1565998115@qq.com', 2.80, 0, 20, '2019-04-17 13:20:13', '2019-04-17 13:19:52', '2019-04-17 13:20:14', NULL, NULL);
INSERT INTO `order_master` VALUES ('1555559700938323783', 3, 6, 'qian', '15659548846', '1565998115@qq.com', 3.20, 0, 10, NULL, '2019-04-18 11:55:00', '2019-04-18 11:55:00', NULL, NULL);
INSERT INTO `order_master` VALUES ('1555559958292844690', 1, 1, 'jack', '178541752659', '1027700603@qq.com', 0.75, 0, 10, NULL, '2019-04-18 11:59:18', '2019-04-18 11:59:18', NULL, NULL);
INSERT INTO `order_master` VALUES ('1555560104991785771', 3, 6, 'qian', '15659548846', '1565998115@qq.com', 0.80, 0, 50, '2019-04-18 12:02:28', '2019-04-18 12:01:44', '2019-06-12 18:44:36', '无法打印', '');
INSERT INTO `order_master` VALUES ('1555560314558531602', 1, 6, 'qian', '15659548846', '1565998115@qq.com', 0.75, 0, 20, '2019-04-18 12:05:33', '2019-04-18 12:05:14', '2019-04-18 12:05:33', NULL, NULL);
INSERT INTO `order_master` VALUES ('1555560693844820038', 1, 6, 'qian', '15659548846', '1565998115@qq.com', 0.75, 0, 20, '2019-04-18 12:12:21', '2019-04-18 12:11:33', '2019-04-18 12:12:21', NULL, NULL);
INSERT INTO `order_master` VALUES ('1557823867212421216', 1, 1, 'jack', '17854176692', '1027700603@qq.com', 9.00, 0, 60, '2019-05-14 16:51:48', '2019-05-14 16:51:07', '2019-05-26 16:33:57', NULL, '5-5-5');
INSERT INTO `order_master` VALUES ('1557824906196370861', 1, 1, 'jack', '17854176692', '1027700603@qq.com', 18.90, 0, 40, '2019-05-14 17:09:46', '2019-05-14 17:08:26', '2019-05-14 17:11:08', NULL, '5-5-5');
INSERT INTO `order_master` VALUES ('1558692581456860403', 2, 1, 'jack', '17854176692', '1027700603@qq.com', 10.00, 0, 20, '2019-05-24 18:24:08', '2019-05-24 18:09:41', '2019-05-24 18:24:11', NULL, NULL);
INSERT INTO `order_master` VALUES ('1558702459139207905', 3, 6, 'qian', '17854176692', '1565998115@qq.com', 9.00, 0, 40, '2019-05-24 20:56:29', '2019-05-24 20:54:19', '2019-05-24 20:57:41', NULL, '5-16-5');
INSERT INTO `order_master` VALUES ('1560499729207430719', 2, 1, 'jack', '17854176692', '1027700603@qq.com', 12.60, 0, 10, NULL, '2019-06-14 16:08:49', '2019-06-14 16:08:49', NULL, NULL);
INSERT INTO `order_master` VALUES ('1560520393636753831', 2, 1, 'jack', '17854176692', '1027700603@qq.com', 9.00, 0, 60, '2019-06-14 21:54:52', '2019-06-14 21:53:13', '2019-06-14 21:59:20', NULL, NULL);
INSERT INTO `order_master` VALUES ('1560528546108167873', 2, 1, 'jack', '17854176692', '1027700603@qq.com', 18.00, 0, 40, '2019-06-15 00:09:56', '2019-06-15 00:09:06', '2019-06-15 00:10:38', NULL, '25-5-5');
INSERT INTO `order_master` VALUES ('1560559466991379053', 3, 1, 'jack', '17854176692', '1027700603@qq.com', 9.00, 0, 40, '2019-06-15 08:44:49', '2019-06-15 08:44:26', '2019-06-15 08:45:01', NULL, '55-555');
INSERT INTO `order_master` VALUES ('1560561217445858169', 2, 1, 'jack', '17854176692', '1027700603@qq.com', 11.70, 0, 60, '2019-06-15 09:14:10', '2019-06-15 09:13:37', '2019-10-05 20:37:21', NULL, '55-55');
INSERT INTO `order_master` VALUES ('1570278360445743493', 2, 1, 'jack', '17854176692', '1027700603@qq.com', 1.00, 0, 60, '2019-10-05 20:27:40', '2019-10-05 20:26:00', '2019-10-05 20:37:12', NULL, '8-9-9');
INSERT INTO `order_master` VALUES ('1570279581467164764', 2, 1, 'jack', '17854176692', '1027700603@qq.com', 2.00, 0, 40, '2019-10-05 20:47:11', '2019-10-05 20:46:21', '2019-10-05 20:47:43', NULL, '66-6-6');
INSERT INTO `order_master` VALUES ('1570281125890357315', 2, 1, 'jack', '17854176692', '1027700603@qq.com', 1.40, 0, 10, NULL, '2019-10-05 21:12:05', '2019-10-05 21:12:05', NULL, NULL);
INSERT INTO `order_master` VALUES ('1570282146039583037', 2, 1, 'jack', '17854176692', '1027700603@qq.com', 2.00, 0, 10, NULL, '2019-10-05 21:29:06', '2019-10-05 21:29:06', NULL, NULL);
INSERT INTO `order_master` VALUES ('1570314782760650869', 2, 1, 'jack', '17854176692', '1027700603@qq.com', 0.70, 0, 20, '2019-10-06 06:33:38', '2019-10-06 06:33:02', '2019-10-06 06:33:39', NULL, NULL);
INSERT INTO `order_master` VALUES ('1570315270152699942', 2, 1, 'jack', '17854176692', '1027700603@qq.com', 1.00, 0, 60, '2019-10-06 06:42:02', '2019-10-06 06:41:10', '2019-10-06 06:45:46', NULL, '6-6-6');

-- ----------------------------
-- Table structure for page_size_info
-- ----------------------------
DROP TABLE IF EXISTS `page_size_info`;
CREATE TABLE `page_size_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `size_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '0->A0 1->A1   2->A2  .... 10->A10 11->4A0  12->0A0 ',
  `shop_id` int(11) DEFAULT NULL,
  `variable` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '-1' COMMENT '相对于 A4 的价格，比如: 0.8 -> 0.8 * A4价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of page_size_info
-- ----------------------------
INSERT INTO `page_size_info` VALUES (1, '0', 1, '1.1');
INSERT INTO `page_size_info` VALUES (2, '1', 1, '1.2');
INSERT INTO `page_size_info` VALUES (3, '2', 1, '1.3');
INSERT INTO `page_size_info` VALUES (4, '3', 1, '1.4');
INSERT INTO `page_size_info` VALUES (5, '4', 1, '1');
INSERT INTO `page_size_info` VALUES (6, '0', 2, '1.1');
INSERT INTO `page_size_info` VALUES (7, '1', 2, '1.2');
INSERT INTO `page_size_info` VALUES (8, '2', 2, '1.2');
INSERT INTO `page_size_info` VALUES (9, '3', 2, '1.3');
INSERT INTO `page_size_info` VALUES (10, '4', 2, '1');
INSERT INTO `page_size_info` VALUES (11, '0', 3, '1');
INSERT INTO `page_size_info` VALUES (12, '1', 3, '1');
INSERT INTO `page_size_info` VALUES (13, '2', 3, '1');
INSERT INTO `page_size_info` VALUES (14, '3', 3, '1');
INSERT INTO `page_size_info` VALUES (15, '4', 3, '1');
INSERT INTO `page_size_info` VALUES (16, '5', 3, '1.1');
INSERT INTO `page_size_info` VALUES (17, '0', 4, '1');
INSERT INTO `page_size_info` VALUES (18, '1', 4, '1');
INSERT INTO `page_size_info` VALUES (19, '2', 4, '1');
INSERT INTO `page_size_info` VALUES (20, '3', 4, '1');
INSERT INTO `page_size_info` VALUES (21, '4', 4, '1');
INSERT INTO `page_size_info` VALUES (22, '5', 4, '1.1');

-- ----------------------------
-- Table structure for pay_info
-- ----------------------------
DROP TABLE IF EXISTS `pay_info`;
CREATE TABLE `pay_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `pay_platform` int(10) DEFAULT NULL,
  `platform_number` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付宝流水号',
  `platform_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付宝支付状态',
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of pay_info
-- ----------------------------
INSERT INTO `pay_info` VALUES (1, 6, '1554877941134453482', 0, '2019041022001410071000028834', 'TRADE_SUCCESS', '2019-04-10 14:33:12', '2019-04-10 14:33:12');
INSERT INTO `pay_info` VALUES (2, 6, '1554878659199238945', 0, '2019041022001410071000030168', 'TRADE_SUCCESS', '2019-04-10 14:45:02', '2019-04-10 14:45:02');
INSERT INTO `pay_info` VALUES (3, 1, '1554878972849361181', 0, '2019041022001410071000027687', 'TRADE_SUCCESS', '2019-04-10 14:49:55', '2019-04-10 14:49:55');
INSERT INTO `pay_info` VALUES (4, 6, '1554879278151669912', 0, '2019041022001410071000030169', 'TRADE_SUCCESS', '2019-04-10 14:55:27', '2019-04-10 14:55:27');
INSERT INTO `pay_info` VALUES (5, 6, '1554879611576239040', 0, '2019041022001410071000028835', 'TRADE_SUCCESS', '2019-04-10 15:00:53', '2019-04-10 15:00:53');
INSERT INTO `pay_info` VALUES (6, 1, '1554882965254163093', 0, '2019041022001410071000030170', 'TRADE_SUCCESS', '2019-04-10 15:56:27', '2019-04-10 15:56:27');
INSERT INTO `pay_info` VALUES (7, 4, '1555477329692141351', 0, '2019041722001410071000042153', 'TRADE_SUCCESS', '2019-04-17 13:02:29', '2019-04-17 13:02:29');
INSERT INTO `pay_info` VALUES (8, 4, '1555477606410286280', 0, '2019041722001410071000042154', 'TRADE_SUCCESS', '2019-04-17 13:07:05', '2019-04-17 13:07:05');
INSERT INTO `pay_info` VALUES (9, 6, '1555478392829527145', 0, '2019041722001410071000042155', 'TRADE_SUCCESS', '2019-04-17 13:20:14', '2019-04-17 13:20:14');
INSERT INTO `pay_info` VALUES (10, 6, '1555560104991785771', 0, '2019041822001410071000042157', 'TRADE_SUCCESS', '2019-04-18 12:02:28', '2019-04-18 12:02:28');
INSERT INTO `pay_info` VALUES (11, 6, '1555560314558531602', 0, '2019041822001410071000043486', 'TRADE_SUCCESS', '2019-04-18 12:05:33', '2019-04-18 12:05:33');
INSERT INTO `pay_info` VALUES (12, 6, '1555560693844820038', 0, '2019041822001410071000043487', 'TRADE_SUCCESS', '2019-04-18 12:12:21', '2019-04-18 12:12:21');
INSERT INTO `pay_info` VALUES (13, 1, '1557823867212421216', 0, '2019051422001410071000077568', 'TRADE_SUCCESS', '2019-05-14 16:51:51', '2019-05-14 16:51:51');
INSERT INTO `pay_info` VALUES (14, 1, '1557824906196370861', 0, '2019051422001410071000077569', 'TRADE_SUCCESS', '2019-05-14 17:09:49', '2019-05-14 17:09:49');
INSERT INTO `pay_info` VALUES (15, 1, '1558692581456860403', 0, '2019052422001410071000094421', 'TRADE_SUCCESS', '2019-05-24 18:24:11', '2019-05-24 18:24:11');
INSERT INTO `pay_info` VALUES (16, 6, '1558702459139207905', 0, '2019052422001410071000097671', 'TRADE_SUCCESS', '2019-05-24 20:56:33', '2019-05-24 20:56:33');
INSERT INTO `pay_info` VALUES (17, 1, '1560520393636753831', 0, '2019061422001410071000101088', 'TRADE_SUCCESS', '2019-06-14 21:54:55', '2019-06-14 21:54:55');
INSERT INTO `pay_info` VALUES (18, 1, '1560528546108167873', 0, '2019061522001410071000102390', 'TRADE_SUCCESS', '2019-06-15 00:09:59', '2019-06-15 00:09:59');
INSERT INTO `pay_info` VALUES (19, 1, '1560559466991379053', 0, '2019061522001410071000101089', 'TRADE_SUCCESS', '2019-06-15 08:44:50', '2019-06-15 08:44:50');
INSERT INTO `pay_info` VALUES (20, 1, '1560561217445858169', 0, '2019061522001410071000102391', 'TRADE_SUCCESS', '2019-06-15 09:14:10', '2019-06-15 09:14:10');
INSERT INTO `pay_info` VALUES (21, 1, '1570278360445743493', 0, '2019100522001410071000120803', 'TRADE_SUCCESS', '2019-10-05 20:27:42', '2019-10-05 20:27:42');
INSERT INTO `pay_info` VALUES (22, 1, '1570279581467164764', 0, '2019100522001410071000121984', 'TRADE_SUCCESS', '2019-10-05 20:47:13', '2019-10-05 20:47:13');
INSERT INTO `pay_info` VALUES (23, 1, '1570314782760650869', 0, '2019100622001410071000121985', 'TRADE_SUCCESS', '2019-10-06 06:33:39', '2019-10-06 06:33:39');
INSERT INTO `pay_info` VALUES (24, 1, '1570315270152699942', 0, '2019100622001410071000120804', 'TRADE_SUCCESS', '2019-10-06 06:42:04', '2019-10-06 06:42:04');

-- ----------------------------
-- Table structure for score_history
-- ----------------------------
DROP TABLE IF EXISTS `score_history`;
CREATE TABLE `score_history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) DEFAULT NULL,
  `share_id` int(11) NOT NULL,
  `integral` int(11) NOT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `look_over` int(4) DEFAULT NULL COMMENT '0 未查看此记录 1 已查看此记录。进入主页的时候会根据这个字段来看此用户有没有新的消息，如果此字段有为 0 的。那么应有一个红点表示！',
  `user_id` int(11) NOT NULL,
  `file_new_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of score_history
-- ----------------------------
INSERT INTO `score_history` VALUES (11, 6, 3, 9, '2019-04-27 16:49:00', '2019-04-27 16:49:00', 0, 1, '397c8ffc-39f2-4b61-bdc4-d97de5e2334b.pdf');
INSERT INTO `score_history` VALUES (12, 1, 2, 3, '2019-04-27 17:00:44', '2019-04-27 17:00:44', 0, 6, '3e98a96b-629f-41f9-b57a-12846110776d.pdf');
INSERT INTO `score_history` VALUES (13, 1, 4, 3, '2019-04-27 17:43:48', '2019-04-27 17:43:48', 0, 6, '0f78e92f-4252-465f-9a91-18743c6420bd.doc');
INSERT INTO `score_history` VALUES (14, 1, 5, 5, '2019-04-29 21:06:46', '2019-04-29 21:06:46', 0, 7, '88a365a4-73ef-446b-95cf-6e894ebe2da0.doc');
INSERT INTO `score_history` VALUES (15, 6, 7, 1, '2019-05-14 17:12:59', '2019-05-14 17:12:59', 0, 1, 'b55f6b58-8d1c-48c8-b2ce-10c1906e9abf.doc');
INSERT INTO `score_history` VALUES (16, 1, 8, 3, '2019-05-24 19:46:13', '2019-05-24 19:46:13', 0, 6, '1046b235-ec3a-4396-bea3-8b5deaf4837a.pdf');
INSERT INTO `score_history` VALUES (17, 1, 6, 9, '2019-05-30 00:33:04', '2019-05-30 00:33:04', 0, 6, '28975455-b193-4549-8cf8-f571b71d08cd.pdf');
INSERT INTO `score_history` VALUES (18, 1, 5, 5, '2019-05-30 00:34:59', '2019-05-30 00:34:59', 0, 6, '88a365a4-73ef-446b-95cf-6e894ebe2da0.doc');
INSERT INTO `score_history` VALUES (19, 6, 10, 3, '2019-06-15 09:15:36', '2019-06-15 09:15:36', 0, 1, '2d614d60-5442-48e6-b6f8-7db0b33ce7dc.pdf');

-- ----------------------------
-- Table structure for share
-- ----------------------------
DROP TABLE IF EXISTS `share`;
CREATE TABLE `share`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `user_id` int(11) NOT NULL,
  `file_id` int(11) NOT NULL,
  `is_delete` int(4) NOT NULL DEFAULT 0 COMMENT '0 未下架 1 下降',
  `tag` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '// 待开发',
  `view_num` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `download_num` int(10) DEFAULT NULL,
  `is_hot` int(4) DEFAULT NULL,
  `is_top` int(4) DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `sub_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of share
-- ----------------------------
INSERT INTO `share` VALUES (2, '2019 考研资料分享！', '2019刚刚上岸，分享自己的考研资料，希望大家多读下载！大部分是真题、考试卷子。2019刚刚上岸，分享自己的考研资料，希望大家多读下载！大部分是真题、考试卷子。2019刚刚上岸，分享自己的考研资料，希望大家多读下载！大部分是真题、考试卷子。', 1, 78, 0, '1', '15', 1, 0, 0, '<p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span></p><p><ol><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">&nbsp;数据结构</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">操作系统</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">计算机组成原理</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">软件工程原理网络原理&nbsp;</font></span></li></ol>下面时数据结构题题目的目录：</p><p><ul><li>&nbsp;链表</li><li>线性表</li><li>栈和队列</li><li>树</li><li>数组</li><li>图</li><li>排序</li><li>查找&nbsp;</li></ul>如果需要更多资料可以添加我的qq：<i><b>1027700603 </b></i><br></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span></p><p></p><ol><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">&nbsp;数据结构</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">操作系统</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">计算机组成原理</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">软件工程原理网络原理&nbsp;</font></span></li></ol><p>下面时数据结构题题目的目录：</p><p></p><p></p><ul><li>&nbsp;链表</li><li>线性表</li><li>栈和队列</li><li>树</li><li>数组</li><li>图</li><li>排序</li><li>查找&nbsp;</li></ul><p>如果需要更多资料可以添加我的qq：<i><b>1027700603&nbsp;</b></i></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span></p><p></p><ol><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">&nbsp;数据结构</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">操作系统</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">计算机组成原理</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">软件工程原理网络原理&nbsp;</font></span></li></ol><p>下面时数据结构题题目的目录：</p><p></p><p></p><ul><li>&nbsp;链表</li><li>线性表</li><li>栈和队列</li><li>树</li><li>数组</li><li>图</li><li>排序</li><li>查找&nbsp;</li></ul><p>如果需要更多资料可以添加我的qq：<i><b>1027700603&nbsp;</b></i></p><p></p><p></p>', '2019-04-22 17:21:39', '2019-05-30 00:37:22', NULL);
INSERT INTO `share` VALUES (3, '济南大学高数期末考试题', '这是大学高数期末考试题，历年考试题。可以用于知识点回顾、巩固知识点。通过学习真题，掌握历年的出题方式，更好的准备期末考试。', 6, 76, 0, '1', '50', 1, 0, 0, '<p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span></p><p><ol><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">&nbsp;数据结构</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">操作系统</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">计算机组成原理</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">软件工程原理网络原理&nbsp;</font></span></li></ol>下面时数据结构题题目的目录：</p><p><ul><li>&nbsp;链表</li><li>线性表</li><li>栈和队列</li><li>树</li><li>数组</li><li>图</li><li>排序</li><li>查找&nbsp;</li></ul>如果需要更多资料可以添加我的qq：<i><b>1027700603 </b></i><br></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span></p><p></p><ol><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">&nbsp;数据结构</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">操作系统</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">计算机组成原理</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">软件工程原理网络原理&nbsp;</font></span></li></ol><p>下面时数据结构题题目的目录：</p><p></p><p></p><ul><li>&nbsp;链表</li><li>线性表</li><li>栈和队列</li><li>树</li><li>数组</li><li>图</li><li>排序</li><li>查找&nbsp;</li></ul><p>如果需要更多资料可以添加我的qq：<i><b>1027700603&nbsp;</b></i></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span></p><p></p><ol><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">&nbsp;数据结构</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">操作系统</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">计算机组成原理</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">软件工程原理网络原理&nbsp;</font></span></li></ol><p>下面时数据结构题题目的目录：</p><p></p><p></p><ul><li>&nbsp;链表</li><li>线性表</li><li>栈和队列</li><li>树</li><li>数组</li><li>图</li><li>排序</li><li>查找&nbsp;</li></ul><p>如果需要更多资料可以添加我的qq：<i><b>1027700603&nbsp;</b></i></p><p></p><p></p>', '2019-04-23 12:44:34', '2019-05-30 00:39:16', NULL);
INSERT INTO `share` VALUES (4, '2019 年大学考试题', '参加了2019年大学软工的考试，有2019届考研真题。软见工程，参加了2019年大学软工的考试，有2019届考研真题。软见工程，参加了2019年软工的考试，有2019届考研真题。', 1, 33, 0, '1', '8', 1, 0, 0, '<p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span></p><p><ol><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">&nbsp;数据结构</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">操作系统</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">计算机组成原理</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" style=\"\" size=\"2\">软件工程原理网络原理&nbsp;</font></span></li></ol>下面时数据结构题题目的目录：</p><p><ul><li>&nbsp;链表</li><li>线性表</li><li>栈和队列</li><li>树</li><li>数组</li><li>图</li><li>排序</li><li>查找&nbsp;</li></ul>如果需要更多资料可以添加我的qq：<i><b>1027700603 </b></i><br></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span></p><p></p><ol><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">&nbsp;数据结构</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">操作系统</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">计算机组成原理</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">软件工程原理网络原理&nbsp;</font></span></li></ol><p>下面时数据结构题题目的目录：</p><p></p><p></p><ul><li>&nbsp;链表</li><li>线性表</li><li>栈和队列</li><li>树</li><li>数组</li><li>图</li><li>排序</li><li>查找&nbsp;</li></ul><p>如果需要更多资料可以添加我的qq：<i><b>1027700603&nbsp;</b></i></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span></p><p></p><ol><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">&nbsp;数据结构</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">操作系统</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">计算机组成原理</font></span></li><li><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255);\"><font face=\"微软雅黑\" size=\"2\">软件工程原理网络原理&nbsp;</font></span></li></ol><p>下面时数据结构题题目的目录：</p><p></p><p></p><ul><li>&nbsp;链表</li><li>线性表</li><li>栈和队列</li><li>树</li><li>数组</li><li>图</li><li>排序</li><li>查找&nbsp;</li></ul><p>如果需要更多资料可以添加我的qq：<i><b>1027700603&nbsp;</b></i></p><p></p><p></p>', '2019-04-27 17:43:00', '2019-05-30 00:37:39', NULL);
INSERT INTO `share` VALUES (5, '大一高数期末考试题库选摘(附详解答案)', '这是大学的高数题，期末考试的题目，每年题型都不变，可以把这个资料当作一首的复习资料。期末考试的题目，每年题型都不变，可以把这个资料当作一首的复习资料。期末考试的题目，每年题型都不变，可以把这个资料当作一首的复习资料。', 1, 89, 0, '2', '6', 2, 0, 0, '<p>这是的高数题，期末考试的题目，每年题型都不变，可以把这个资料当作一首的复习资料。期末考试的题目，每年题型都不变，可以把这个资料当作一首的复习资料。</p><p><br></p><p>高等数学（它是几门课程的总称）是理、工科院校一门重要的基础学科，也是非数学专业理工科专业学生的必修数学课,也是其它某些专业的必修课。<br></p><p><br></p><p>主要内容包括：</p><p><ol><li>数列</li><li>极限</li><li>微积分</li><li>空间解析几何与线性代数</li><li>级数</li><li>常微分方程</li></ol>初等数学研究的是常量与匀变量，高等数学研究的是非匀变量。</p><p><br></p><p>高等数学（它是几门课程的总称）是理、工科院校一门重要的基础学科，也是非数学专业理工科专业学生的必修数学课,也是其它某些专业的必修课。</p><p><br></p><p>这是济南大学的高数题，期末考试的题目，每年题型都不变，可以把这个资料当作一首的复习资料。期末考试的题目，每年题型都不变，可以把这个资料当作一首的复习资料。</p><p><br></p><p>高等数学（它是几门课程的总称）是理、工科院校一门重要的基础学科，也是非数学专业理工科专业学生的必修数学课,也是其它某些专业的必修课。<br></p><p><br></p><p>这是济南大学的高数题，期末考试的题目，每年题型都不变，可以把这个资料当作一首的复习资料。期末考试的题目，每年题型都不变，可以把这个资料当作一首的复习资料。</p><div><p><br></p></div><p>高等数学（它是几门课程的总称）是理、工科院校一门重要的基础学科，也是非数学专业理工科专业学生的必修数学课,也是其它某些专业的必修课。<br></p>', '2019-04-29 21:06:08', '2019-05-30 00:37:30', NULL);
INSERT INTO `share` VALUES (6, '软件专业 thymeleaf 技术文档分享', '软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享。', 1, 77, 0, '0', '9', 1, 0, 0, '<p>文件介绍：</p><p><br></p><p>软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享。<br><br></p><p><br></p><p>文件目录：</p><p><br></p><p><ul><li>软件专业 thymeleaf 技术文档分享</li><li>软件专业 thymeleaf 技术文档分享</li><li>软件专业 thymeleaf 技术文档分享</li><li>软件专业 thymeleaf 技术文档分享</li><li>软件专业 thymeleaf 技术文档分享</li><li>软件专业 thymeleaf 技术文档分享</li></ul></p><p><br></p><p>主要内容：</p><p><br></p><p>软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享，软件专业 thymeleaf 技术文档分享。<br><br></p>', '2019-05-02 13:22:43', '2019-05-30 00:34:15', NULL);
INSERT INTO `share` VALUES (7, '2019 年大学软件工程考试真题', '2019年大学软件工程考试真题，2019年软件工程考试真题，2019年软件工程考试真题，2019年软件工程考试真题。', 6, 88, 0, '1', '12', 1, 0, 0, '<p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></p><ol style=\"margin: 0px 0px 0px 1.3em; padding: 0px; list-style-position: initial; list-style-image: initial; color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\"><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">&nbsp;数据结构</font></span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">操作系统</font></span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">计算机组成原理</font></span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">软件工程原理网络原理&nbsp;</font></span></li></ol><p><span style=\"color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\">下面时数据结构题题目的目录：</span></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></p><ul style=\"margin: 0px 0px 0px 1.3em; padding: 0px; list-style-position: initial; list-style-image: initial; color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\"><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">&nbsp;链表</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">线性表</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">栈和队列</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">树</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">数组</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">图</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">排序</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">查找&nbsp;</li></ul><p><span style=\"color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\">如果需要更多资料可以添加我的qq：</span><span style=\"color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\"><span style=\"font-weight: 700; color: rgb(0, 0, 0);\">1027700603&nbsp;</span></span><br style=\"color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\"></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></p><ol style=\"margin: 0px 0px 0px 1.3em; padding: 0px; list-style-position: initial; list-style-image: initial; color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\"><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">&nbsp;数据结构</font></span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">操作系统</font></span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">计算机组成原理</font></span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">软件工程原理网络原理&nbsp;</font></span></li></ol><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\">下面时数据结构题题目的目录：</p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></p><ul style=\"margin: 0px 0px 0px 1.3em; padding: 0px; list-style-position: initial; list-style-image: initial; color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\"><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">&nbsp;链表</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">线性表</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">栈和队列</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">树</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">数组</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">图</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">排序</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">查找&nbsp;</li></ul><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\">如果需要更多资料可以添加我的qq：<span style=\"font-weight: 700; color: rgb(0, 0, 0);\">1027700603&nbsp;</span></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></p><ol style=\"margin: 0px 0px 0px 1.3em; padding: 0px; list-style-position: initial; list-style-image: initial; color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\"><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">&nbsp;数据结构</font></span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">操作系统</font></span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">计算机组成原理</font></span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">软件工程原理网络原理&nbsp;</font></span></li></ol><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\">下面时数据结构题题目的目录：</p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></p><ul style=\"margin: 0px 0px 0px 1.3em; padding: 0px; list-style-position: initial; list-style-image: initial; color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\"><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">&nbsp;链表</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">线性表</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">栈和队列</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">树</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">数组</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">图</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">排序</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">查找&nbsp;</li></ul><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\">如果需要更多资料可以添加我的qq：<span style=\"font-weight: 700; color: rgb(0, 0, 0);\">1027700603&nbsp;</span></p>', '2019-05-14 16:57:26', '2019-06-15 09:05:47', NULL);
INSERT INTO `share` VALUES (8, '考公资料分享~', '考公资料分享，刚刚考公结束，网上收集到的考公资料，分享给大家。考公资料分享，刚刚考公结束，网上收集到的考公资料，分享给大家。考公资料分享，刚刚考公结束，网上收集到的考公资料，分享给大家。', 1, 95, 0, '4', '7', 1, 0, 0, '<p>考公资料分享，刚刚考公结束，网上收集到的考公资料，分享给大家。考公资料分享，刚刚考公结束，网上收集到的考公资料，分享给大家。资料分为下面几类：</p><p><ul><li>行政职业能力测试</li><li>申论</li><li>公共基础知识</li></ul><span style=\"font-size: 14px;\">其中有几个模块非常不错，对我的提供很多，这些章节模块是：</span></p><p><ul><li><span style=\"font-size: 14px;\">行测难题大全</span></li><li><span style=\"font-size: 14px;\">资料分析必备利器</span></li><li><span style=\"font-size: 14px;\">必备十篇范文</span></li><li><span style=\"font-size: 14px;\">判断推理必会的技巧</span></li></ul><span style=\"font-size: 14px;\"><div style=\"text-align: left;\">除此之外，大家有问题可以私聊我：<b><i>102xx455</i></b></div></span></p><p style=\"margin-top: 20px; margin-bottom: 20px; padding: 0px; color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft YaHei&quot;, arial, 宋体, sans-serif, tahoma; font-size: 16px; background-color: rgb(255, 255, 255);\"><br></p>', '2019-05-24 19:14:02', '2019-06-15 09:05:40', NULL);
INSERT INTO `share` VALUES (9, '考公资料分享-Jack', '考公资料分享，刚刚考公结束，网上收集到的考公资料，分享给大家。考公资料分享，刚刚考公结束，网上收集到的考公资料，分享给大家。', 1, 90, 0, '4', '49', 0, 0, 0, '<p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\">考公资料分享，刚刚考公结束，网上收集到的考公资料，分享给大家。考公资料分享，刚刚考公结束，网上收集到的考公资料，分享给大家。资料分为下面几类：</p><ul style=\"margin: 0px 0px 0px 1.3em; padding: 0px; list-style-position: initial; list-style-image: initial; color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\"><li>行政职业能力测试</li><li>申论</li><li>公共基础知识</li></ul><p><span style=\"color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\">其中有几个模块非常不错，对我的提供很多，这些章节模块是：</span></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></p><ul style=\"margin: 0px 0px 0px 1.3em; padding: 0px; list-style-position: initial; list-style-image: initial; color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\"><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"font-size: 14px;\">行测难题大全</span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"font-size: 14px;\">资料分析必备利器</span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"font-size: 14px;\">必备十篇范文</span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"font-size: 14px;\">判断推理必会的技巧</span></li></ul><p><span style=\"color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></span></p><div style=\"text-align: left;\">除此之外，大家有问题可以私聊我：<b style=\"font-weight: 700; color: rgb(0, 0, 0);\"><i style=\"font-style: normal;\">102xx455</i></b></div>', '2019-05-30 00:40:30', '2019-10-06 06:45:13', NULL);
INSERT INTO `share` VALUES (10, '软件工程考试真题分享~', '2019年大学软件工程考试真题，具体科目： 数据结构、操作系统、计算机组成原理、软件工程原理网络原理 ，2019年济南大学软件工程考试真题', 6, 97, 0, '1', '5', 1, 0, 0, '<p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span><span style=\"font-family: 微软雅黑; font-size: small; color: rgb(51, 51, 51);\">&nbsp;数据结构、</span><span style=\"font-family: 微软雅黑; font-size: small; color: rgb(51, 51, 51);\">操作系统、</span><span style=\"font-family: 微软雅黑; font-size: small; color: rgb(51, 51, 51);\">计算机组成原理、</span><span style=\"font-family: 微软雅黑; font-size: small; color: rgb(51, 51, 51);\">软件工程原理网络原理&nbsp;</span></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"><span style=\"color: rgb(51, 51, 51);\">下面时数据结构题题目的目录：</span>链表、线性表、栈和队列、树、数组、图、排序、查找 ，软件工程文件示意图为：</p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"><img src=\"http://image.qian.com/218e73bf-1c7d-4500-99b2-84eb6f6f478d.png\" style=\"max-width:100% !important;height:auto;\"><br></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span><span style=\"font-family: 微软雅黑; font-size: small; color: rgb(51, 51, 51);\">&nbsp;数据结构、</span><span style=\"font-family: 微软雅黑; font-size: small; color: rgb(51, 51, 51);\">操作系统、</span><span style=\"font-family: 微软雅黑; font-size: small; color: rgb(51, 51, 51);\">计算机组成原理、</span><span style=\"font-family: 微软雅黑; font-size: small; color: rgb(51, 51, 51);\">软件工程原理网络原理&nbsp;</span></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"><span style=\"color: rgb(51, 51, 51);\">下面时数据结构题题目的目录：</span>链表、线性表、栈和队列、树、数组、图、排序、查找</p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"><span style=\"color: rgb(51, 51, 51);\"><font face=\"微软雅黑\" size=\"2\">2019年济南大学软件工程考试真题，具体科目：</font></span><span style=\"font-family: 微软雅黑; font-size: small; color: rgb(51, 51, 51);\">&nbsp;数据结构、</span><span style=\"font-family: 微软雅黑; font-size: small; color: rgb(51, 51, 51);\">操作系统、</span><span style=\"font-family: 微软雅黑; font-size: small; color: rgb(51, 51, 51);\">计算机组成原理、</span><span style=\"font-family: 微软雅黑; font-size: small; color: rgb(51, 51, 51);\">软件工程原理网络原理&nbsp;</span></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"><span style=\"color: rgb(51, 51, 51);\">下面时数据结构题题目的目录：</span>链表、线性表、栈和队列、树、数组、图、排序、查找</p>', '2019-06-15 09:07:10', '2019-10-05 20:34:34', NULL);
INSERT INTO `share` VALUES (11, '考公资料分享啦~~', '考公资料分享，刚刚考公结束，网上收集到的考公资料，分享给大家。考公资料分享，刚刚考公结束，网上收集到的考公资料，分享给大家', 1, 104, 0, '2', '2', 0, 0, 0, '<p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\">考公资料分享，刚刚考公结束，网上收集到的考公资料，分享给大家。考公资料分享，刚刚考公结束，网上收集到的考公资料，分享给大家。资料分为下面几类：</p><ul style=\"margin: 0px 0px 0px 1.3em; padding: 0px; list-style-position: initial; list-style-image: initial; color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\"><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">行政职业能力测试</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">申论</li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\">公共基础知识</li></ul><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"><span style=\"color: rgb(51, 51, 51);\">其中有几个模块非常不错，对我的提供很多，这些章节模块是：</span></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></p><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"></p><ul style=\"margin: 0px 0px 0px 1.3em; padding: 0px; list-style-position: initial; list-style-image: initial; color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\"><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"font-size: 14px;\">行测难题大全</span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"font-size: 14px;\">资料分析必备利器</span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"font-size: 14px;\">必备十篇范文</span></li><li style=\"margin: 0px; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; text-align: justify;\"><span style=\"font-size: 14px;\">判断推理必会的技巧</span></li></ul><p style=\"margin-top: 1.2em; margin-bottom: 0.5em; padding: 0px; color: rgb(76, 76, 76); line-height: 32px; font-size: 16px; text-align: justify; font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; background-color: rgb(255, 255, 255);\"><span style=\"color: rgb(51, 51, 51);\"></span></p><div style=\"color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft Yahei&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, Helvetica, sans-serif; font-size: 16px; background-color: rgb(255, 255, 255);\">除此之外，大家有问题可以私聊我：<span style=\"font-weight: 700; color: rgb(0, 0, 0);\">102xx455</span></div>', '2019-10-05 20:35:47', '2019-10-05 20:35:51', NULL);

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) NOT NULL,
  `shop_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shop_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shop_description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `credit` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店铺评分',
  `status` int(4) NOT NULL DEFAULT 0 COMMENT '0 接单 1 不接单',
  `work_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `close_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_receive_order` int(4) NOT NULL DEFAULT 1 COMMENT '非营业时间是否自动接单？ 0 接单 1 不接单',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `credit_people_num` int(11) DEFAULT NULL COMMENT '多少人评分',
  `deal_num` int(11) DEFAULT NULL COMMENT '交易数量',
  `sub_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '副图，店铺列表',
  `main_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主图，店铺详情',
  `mini_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '店铺详情，店铺头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES (1, 2, '大学打印西门店', 'B站总部西门往东500m', '价格优惠，好评如潮', '<div class=\"shop-detail-content\">\r\n									<div id=\"article-content\" class=\"_2rqEo0e0_0\">\r\n										<div class=\"text\">\r\n											<p>此打印店位于大学西门向东500米处，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。<strong>店铺广受好评，价格实惠、好评如潮。</strong>下面是此店铺店面截屏，大学西门向东500米：</p>\r\n											<p><img src=\"http://image.qian.com/shop01_sub.png\" style=\"width: 720px;height: 343px;\"></p>\r\n											<p>此打印店位于大学西门向东500米处，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。下面是此店铺店面截屏，大学西门向东500米：</p>\r\n											<p>此打印店位于大学西门向东500米处，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。店铺广受好评，价格实惠、好评如潮。下面是此店铺店面截屏，大学西门向东500米：</p>\r\n											<p>此打印店位于大学西门向东500米处，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。<strong>店铺广受好评</strong>下面是此店铺店面截屏，大学西门向东500米：</p>\r\n											<h2>店主备注</h2>\r\n											<ol class=\"js-audit\">\r\n												<li>本店除打印此外还提供复印、印刷服务</li>\r\n												<li>本店寒暑假暂时停业，在校期间正常营业</li>\r\n												<li>小本生意，一经打印，概不退款。</li>\r\n												<li>此处是店主备注的信息</li>\r\n											</ol>\r\n										</div>\r\n									</div>\r\n								</div>', '4.5', 0, '07:00', '20:00', 0, '2019-03-30 20:12:48', '2019-03-30 20:12:50', 8, 21, 'shop01_sub.png', 'shop01_sub.png', 'shop01_mini.png');
INSERT INTO `shop` VALUES (2, 3, '大学八食堂店', 'B站总部八食堂对过打印店', '省时间、高效、实惠', '<div class=\"shop-detail-content\">\r\n									<div id=\"article-content\" class=\"_2rqEo0e0_0\">\r\n										<div class=\"text\">\r\n											<p>此打印店位于大学八食堂。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。<strong>店铺广受好评，价格实惠、好评如潮。</strong>下面是此店铺店面截屏，大学西门向东500米：</p>\r\n											<p><img src=\"http://image.qian.com/shop02_sub.png\" style=\"width: 720px;height: 343px;\"></p>\r\n											<p>此打印店位于大学八食堂，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。下面是此店铺店面截屏，大学西门向东500米：</p>\r\n											<p>此打印店位于大学八食堂，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。店铺广受好评，价格实惠、好评如潮。下面是此店铺店面截屏，大学西门向东500米：</p>\r\n											<p>此打印店位于大学八食堂，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。<strong>店铺广受好评</strong>下面是此店铺店面截屏，大学西门向东500米：</p>\r\n											<h2>店主备注</h2>\r\n											<ol class=\"js-audit\">\r\n												<li>本店除打印此外还提供复印、印刷服务</li>\r\n												<li>本店寒暑假暂时停业，在校期间正常营业</li>\r\n												<li>小本生意，一经打印，概不退款。</li>\r\n												<li>此处是店主备注的信息</li>\r\n											</ol>\r\n										</div>\r\n									</div>\r\n								</div>', '4.7', 0, '07:30', '20:30', 0, '2019-03-30 20:50:35', '2019-03-30 20:50:40', 7, 19, 'shop02_sub.png', 'shop02_sub.png', 'shop02_mini.png');
INSERT INTO `shop` VALUES (3, 4, '大学南苑店面', '南苑店面，南苑住宿区内', '好评如潮、价格实惠、欢迎下单', '<div class=\"shop-detail-content\">\r\n									<div id=\"article-content\" class=\"_2rqEo0e0_0\">\r\n										<div class=\"text\">\r\n											<p>此打印店位于大学南苑，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。<strong>店铺广受好评，价格实惠、好评如潮。</strong>下面是此店铺店面截屏，大学西门向东500米：</p>\r\n											<p><img src=\"http://image.qian.com/shop03_sub.png\" style=\"width: 720px;height: 343px;\"></p>\r\n											<p>此打印店位于大学南苑，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。下面是此店铺店面截屏，济南大学西门向东500米：</p>\r\n											<p>此打印店位于大学南苑，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。店铺广受好评，价格实惠、好评如潮。下面是此店铺店面截屏，大学西门向东500米：</p>\r\n											<p>此打印店位于大学南苑，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。<strong>店铺广受好评</strong>下面是此店铺店面截屏，大学西门向东500米：</p>\r\n											<h2>店主备注</h2>\r\n											<ol class=\"js-audit\">\r\n												<li>本店除打印此外还提供复印、印刷服务</li>\r\n												<li>本店寒暑假暂时停业，在校期间正常营业</li>\r\n												<li>小本生意，一经打印，概不退款。</li>\r\n												<li>此处是店主备注的信息</li>\r\n											</ol>\r\n										</div>\r\n									</div>\r\n								</div>', '3.5', 0, '08:00', '20:10', 0, '2019-03-31 10:10:52', '2019-03-31 10:10:56', 21, 88, 'shop03_sub.png', 'shop03_sub.png', 'shop03_mini.png');
INSERT INTO `shop` VALUES (4, 5, '大学南门店', 'B站总部西走200米处', '大学南门店相关描述....', '<div class=\"shop-detail-content\">\r\n									<div id=\"article-content\" class=\"_2rqEo0e0_0\">\r\n										<div class=\"text\">\r\n											<p>此打印店位于大学西门向东500米处，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。<strong>店铺广受好评，价格实惠、好评如潮。</strong>下面是此店铺店面截屏，大学西门向东500米：</p>\r\n										\r\n											<h2>店主备注</h2>\r\n											<ol class=\"js-audit\">\r\n												<li>本店除打印此外还提供复印、印刷服务</li>\r\n												<li>本店寒暑假暂时停业，在校期间正常营业</li>\r\n											\r\n											</ol>\r\n										</div>\r\n									</div>\r\n			\r\n<div class=\"shop-detail-content\">\r\n									<div id=\"article-content\" class=\"_2rqEo0e0_0\">\r\n										<div class=\"text\">\r\n											<p>此打印店位于济南大学西门向东500米处，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。<strong>店铺广受好评，价格实惠、好评如潮。</strong>下面是此店铺店面截屏，大学西门向东500米：</p>\r\n<p>此打印店位于大学南门向东500米处，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。<strong>店铺广受好评，价格实惠、好评如潮。</strong>下面是此店铺店面截屏，大学西门向东500米：</p>\r\n<p>此打印店位于大学南门向东500米处，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。<strong>店铺广受好评，价格实惠、好评如潮。</strong>下面是此店铺店面截屏，大学西门向东500米：</p>\r\n<p>此打印店位于大学南门向东500米处，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。<strong>店铺广受好评，价格实惠、好评如潮。</strong>下面是此店铺店面截屏，大学西门向东500米：</p>\r\n<p>此打印店位于大学南门向东500米处，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。<strong>店铺广受好评，价格实惠、好评如潮。</strong>下面是此店铺店面截屏，大学西门向东500米：</p>\r\n<p>此打印店位于大学南门向东500米处，位于华联超市附近。此店面承接打印、彩打、PPT、Word、PDF等样式打印，营业时间为：早上7：00-晚上8：00。<strong>店铺广受好评，价格实惠、好评如潮。</strong>下面是此店铺店面截屏，大学西门向东500米：</p>\r\n\r\n										\r\n											<h2>店主备注</h2>\r\n											<ol class=\"js-audit\">\r\n												<li>本店除打印此外还提供复印、印刷服务</li>\r\n												<li>本店寒暑假暂时停业，在校期间正常营业</li>\r\n											\r\n											</ol>\r\n										</div>\r\n									</div>\r\n								</div>					</div>', '2.5', 1, '07:20', '20:20', 0, '2019-03-31 17:50:44', '2019-03-31 17:50:46', 10, 12, 'shop04_sub.png', 'shop04_sub.png', 'shop04_mini.png');

-- ----------------------------
-- Table structure for single_double_info
-- ----------------------------
DROP TABLE IF EXISTS `single_double_info`;
CREATE TABLE `single_double_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '单双页',
  `page_type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '0 单页 1双页',
  `shop_id` int(11) DEFAULT NULL,
  `variable` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单页为 1 就是相对于 黑白/彩色 *1，如果是双页就是 黑白/彩色 价格*index的值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of single_double_info
-- ----------------------------
INSERT INTO `single_double_info` VALUES (1, '0', 1, '1');
INSERT INTO `single_double_info` VALUES (2, '1', 1, '1.5');
INSERT INTO `single_double_info` VALUES (3, '0', 2, '1');
INSERT INTO `single_double_info` VALUES (4, '1', 2, '1.4');
INSERT INTO `single_double_info` VALUES (5, '0', 3, '1');
INSERT INTO `single_double_info` VALUES (6, '1', 3, '1.6');
INSERT INTO `single_double_info` VALUES (7, '0', 4, '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `header_pic` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `question` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `answer` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `role` int(4) NOT NULL DEFAULT 0 COMMENT '0 普通用户  10 店主',
  `integral` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '积分默认为0，上传文件来获得积分',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'jack', 'jack', '1000603@qq.com', '1785415454', 'user01_header.jpg', '我的真实名字？', '王久一', 0, '6', '2019-03-29 16:13:00', '2019-06-15 09:15:36');
INSERT INTO `user` VALUES (2, 'wang', 'wang', '1025566956@qq.com', '1785415454', 'user01_header.jpg', '真实名字？', '王久一', 1, '9', '2019-03-30 20:10:37', '2019-03-30 20:10:40');
INSERT INTO `user` VALUES (3, 'zhang', 'zhang', '1515151515@qq.com', '1785415454', 'user01_header.jpg', 'l真实名字？', '张一', 1, '0', '2019-03-30 20:46:06', '2019-03-30 20:46:09');
INSERT INTO `user` VALUES (4, 'marry', 'marry', '2625595959@qq.com', '1785415454', 'user01_header.jpg', '我的生日？', '12.28', 1, '0', '2019-03-31 10:08:36', '2019-05-25 20:34:40');
INSERT INTO `user` VALUES (5, 'biao', 'biao', '1515626565@qq.com', '1785415454', 'user01_header.jpg', '第一辆车？', '宝马', 1, '0', '2019-03-31 17:51:59', '2019-03-31 17:52:02');
INSERT INTO `user` VALUES (6, 'qian', 'qian', '1565998115@qq.com', '1785415454', 'user02_header.jpg', '你的名字？', '王久一', 0, '5', '2019-04-09 01:27:12', '2019-06-15 09:15:36');
INSERT INTO `user` VALUES (7, 'tom', 'tom', '15659551@qq.com', '1785415454', 'user01_header.jpg', '你的名字？', 'tom', 0, '4', '2019-04-29 20:49:37', '2019-04-29 21:06:46');

SET FOREIGN_KEY_CHECKS = 1;
