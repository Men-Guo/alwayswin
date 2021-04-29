-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: alwayswin.clwhemztsbpv.us-east-1.rds.amazonaws.com    Database: alwayswin
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED='';

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `aid` int NOT NULL AUTO_INCREMENT,
  `uid` int DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state` enum('AL','AK','AZ','AR','CA','CO','CT','DE','FL','GA','HI','ID','IL','IN','IA','KS','KY','LA','ME','MD','MA','MI','MN','MS','MO','MT','NE','NV','NH','NJ','NM','NY','NC','ND','OH','OK','OR','PA','RI','SC','SD','TN','TX','UT','VT','VA','WA','WV','WI','WY') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `zipcode` varchar(45) NOT NULL,
  PRIMARY KEY (`aid`) USING BTREE,
  KEY `uid` (`uid`) USING BTREE,
  CONSTRAINT `uid` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,1,'Arthur','110','500 Campus Dr','CA','92612'),(2,1,'Arthur','9491234567','499 Campus Dr','CA','92612'),(3,2,'Ben','9491234567','500 Campus Dr','CA','92612'),(4,2,'Bob','9491234567','500 Campus Dr','CA','92612'),(5,3,'Cindy','9491234567','500 Campus Dr','CA','92612'),(6,4,'Doug','9491234567','500 Campus Dr','CA','92612'),(8,6,'Father','9491234567','500 Campus Dr','CA','92612'),(9,7,'Grace','9491234567','500 Campus Dr','CA','92612'),(10,8,'Helen','9491234567','500 Campus Dr','CA','92612'),(11,9,'Vera','9491234567','500 Campus Dr','CA','92612');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bidding`
--

DROP TABLE IF EXISTS `bidding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bidding` (
  `bid` int NOT NULL AUTO_INCREMENT,
  `pid` int NOT NULL,
  `uid` int NOT NULL,
  `offer` double NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`bid`),
  KEY `bid_pid_idx` (`pid`,`create_time` DESC),
  KEY `bid_uid_idx` (`uid`,`create_time` DESC),
  CONSTRAINT `bid_pid_fk` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`),
  CONSTRAINT `bid_uid_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='出价表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bidding`
--

LOCK TABLES `bidding` WRITE;
/*!40000 ALTER TABLE `bidding` DISABLE KEYS */;
INSERT INTO `bidding` VALUES (1,11,2,1500,'2021-04-11 00:00:00'),(2,11,1,2000,'2021-04-12 00:00:00'),(3,12,1,3000,'2021-04-12 00:00:00'),(4,13,9,4000,'2021-04-12 00:00:00');
/*!40000 ALTER TABLE `bidding` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `figure`
--

DROP TABLE IF EXISTS `figure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `figure` (
  `fid` int NOT NULL AUTO_INCREMENT,
  `pid` int NOT NULL,
  `url` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_thumbnail` bit(1) NOT NULL DEFAULT b'1' COMMENT 'set to thumbnail by default',
  `updated_time` datetime NOT NULL COMMENT 'Last updated time',
  PRIMARY KEY (`fid`),
  KEY `figure_pid` (`pid`),
  CONSTRAINT `figure_pid_fk` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `figure`
--

LOCK TABLES `figure` WRITE;
/*!40000 ALTER TABLE `figure` DISABLE KEYS */;
INSERT INTO `figure` VALUES (1,1,'https://d3d71ba2asa5oz.cloudfront.net/12003181/images/iphxr-bl.png','black','','1970-01-01 00:00:00'),(2,2,'https://d3d71ba2asa5oz.cloudfront.net/12003181/images/iphxr-bl.png','black','','2021-04-15 00:00:00'),(3,3,'https://d3d71ba2asa5oz.cloudfront.net/12003181/images/iphxr-b.png','blue','','2021-04-15 00:00:00'),(4,4,'https://d3d71ba2asa5oz.cloudfront.net/12003181/images/iphxr-b.png','blue','','2021-04-15 00:00:00'),(5,5,'https://d3d71ba2asa5oz.cloudfront.net/12003181/images/iphxr-b.png','blue','','2021-04-15 00:00:00'),(6,6,'https://d3d71ba2asa5oz.cloudfront.net/12003181/images/iphxr-c.png','orange','','2021-04-15 00:00:00'),(7,7,'https://d3d71ba2asa5oz.cloudfront.net/12003181/images/iphxr-c.png','orange','','2021-04-15 00:00:00'),(8,8,'https://i.ebayimg.com/images/g/fW4AAOSwyl9ga7i5/s-l500.jpg',NULL,'','2021-04-15 00:00:00'),(9,9,'https://i.ebayimg.com/images/g/fW4AAOSwyl9ga7i5/s-l500.jpg',NULL,'','2021-04-15 00:00:00'),(10,10,'https://i.ebayimg.com/images/g/fW4AAOSwyl9ga7i5/s-l500.jpg','aaaaaaa','','2021-04-15 00:00:00'),(11,1,'https://i.ebayimg.com/images/g/sZEAAOSw6iZfkZaT/s-l640.jpg','fake','\0','2021-04-15 00:00:00'),(12,2,'https://i.ebayimg.com/images/g/sZEAAOSw6iZfkZaT/s-l640.jpg','fake','\0','2021-04-15 00:00:00');
/*!40000 ALTER TABLE `figure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `oid` int NOT NULL AUTO_INCREMENT,
  `number` varchar(45) NOT NULL,
  `uid` int NOT NULL,
  `pid` int NOT NULL,
  `address` varchar(512) NOT NULL,
  `payment` double NOT NULL,
  `create_time` datetime NOT NULL,
  `status` enum('placed','paid','shipped','received') NOT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `number_UNIQUE` (`number`),
  KEY `order_pid_fk_idx` (`pid`),
  KEY `order_uid` (`uid`,`create_time` DESC),
  CONSTRAINT `order_pid_fk` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`),
  CONSTRAINT `order_uid_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'20210412XYZ01',1,11,'Campus Dr ',2000,'2021-04-12 00:00:00','placed'),(2,'20210412XYZ02',1,12,'Campus Dr',3000,'2021-04-12 00:00:00','paid'),(3,'20210412XYZ03',9,13,'Campus Dr',4000,'2021-04-12 00:00:00','shipped'),(4,'20210412XYZ04',3,4,'Campus Dr',202,'2021-04-15 00:00:00','paid');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `pid` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(3000) DEFAULT NULL,
  `cate_1` enum('camera','cell phone','accessory','computer','tablet','network hardware','tv','smart home','portable audio','car electronics','gaming console','vr','others') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cate_2` varchar(255) DEFAULT NULL,
  `cate_3` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `start_price` double NOT NULL,
  `auto_win_price` double DEFAULT NULL,
  `reserved_price` double DEFAULT NULL,
  `min_increment` double DEFAULT '1' COMMENT '最少加价',
  `is_passed` bit(1) NOT NULL DEFAULT b'1' COMMENT 'Passed the censor or not.\n1 for passed and 1 is the default value.',
  `is_canceled` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`pid`),
  KEY `product_uid` (`uid`),
  KEY `cate_1` (`cate_1`),
  KEY `price` (`start_price`,`auto_win_price`,`reserved_price`),
  KEY `start_time` (`start_time` DESC),
  CONSTRAINT `product_uid` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,1,'Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included. This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone is fully functional with all carrier networks. It does not come with a SIM card, you will need to get one from your carrier of choice.','cell phone',NULL,NULL,'2021-04-15 00:00:00','2021-04-15 00:00:00','2021-06-15 23:59:59',399,599,499,20,'','\0'),(2,1,'Apple iPhone XR 256GB Factory Unlocked','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included.','cell phone',NULL,NULL,'2021-04-10 00:00:00','2021-04-15 00:00:00','2021-06-14 23:59:59',499,699,599,30,'','\0'),(3,10,'Apple iPhone 7 256GB Factory Unlocked Smartphone 4G LTE iOS Smartphone','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included.','cell phone',NULL,NULL,'2021-04-10 00:00:00','2021-04-15 00:00:00','2021-06-15 23:59:59',499,699,599,30,'','\0'),(4,2,'Apple iPhone 8 256GB Factory Unlocked Smartphone 4G LTE iOS Smartphone','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included.','cell phone',NULL,NULL,'2021-04-10 00:00:00','2021-04-15 00:00:00','2021-06-15 23:59:59',499,699,599,30,'','\0'),(5,2,'Apple iPhone X 256GB Factory Unlocked Smartphone 4G LTE iOS Smartphone','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included.','cell phone',NULL,NULL,'2021-04-10 00:00:00','2021-04-15 00:00:00','2021-06-15 23:59:59',499,699,599,30,'','\0'),(6,3,'Apple iPhone XS 256GB Factory Unlocked Smartphone 4G LTE iOS Smartphone','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included.','cell phone',NULL,NULL,'2021-04-10 00:00:00','2021-04-15 00:00:00','2021-06-15 23:59:59',499,699,599,30,'','\0'),(7,4,'Apple iPhone 11 256GB Factory Unlocked Smartphone 4G LTE iOS Smartphone','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included.','cell phone',NULL,NULL,'2021-04-10 00:00:00','2021-04-15 00:00:00','2021-06-15 23:59:59',499,699,599,30,'','\0'),(8,1,'Bolt Drone FPV Racing Drone Carbon Fiber with First Person View Goggles 5.8 Ghz','Only have flown this drone 1 time. Flew great and fast! Around 30 mph. I have since bought other drones so I no longer would need to keep this one around','camera',NULL,NULL,'2021-04-15 00:00:00','2021-04-16 00:00:00','2021-06-15 23:59:59',100,300,150,10,'','\0'),(9,2,'Bolt Drone FPV Racing Drone Carbon Fiber with First Person View Goggles 5.8 Ghz','Only have flown this drone 1 time. Flew great and fast! Around 30 mph. I have since bought other drones so I no longer would need to keep this one around','camera',NULL,NULL,'2021-04-15 00:00:00','2021-04-16 00:00:00','2021-06-15 23:59:59',100,300,150,10,'','\0'),(10,10,'Bolt Drone FPV Racing Drone Carbon Fiber with First Person View Goggles 5.8 Ghz','Only have flown this drone 1 time. Flew great and fast! Around 30 mph. I have since bought other drones so I no longer would need to keep this one around','camera',NULL,NULL,'2021-04-15 00:00:00','2021-04-16 00:00:00','2021-06-15 23:59:59',100,300,150,10,'','\0'),(11,2,'Play Station 1000','SOLD!','gaming console',NULL,NULL,'2021-04-10 00:00:00','2021-04-10 00:00:00','2021-06-15 23:59:59',1000,10000,NULL,10,'','\0'),(12,2,'Play Station 2000','SOLD!','gaming console',NULL,NULL,'2021-04-10 00:00:00','2021-04-10 00:00:00','2021-06-15 23:59:59',2000,20000,NULL,10,'','\0'),(13,2,'Play Station 3000','SOLD!','gaming console',NULL,NULL,'2021-04-10 00:00:00','2021-04-10 00:00:00','2021-06-15 23:59:59',3000,30000,NULL,10,'','\0'),(15,1,'AA','test','cell phone','','','1970-01-01 00:00:00','1970-01-01 00:00:00','1970-01-01 00:00:00',0,0,0,1,'','\0'),(16,1,'AA','test','cell phone','','','1970-01-01 00:00:00','1970-01-01 00:00:00','1970-01-01 00:00:00',0,0,0,1,'','\0');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_status`
--

DROP TABLE IF EXISTS `product_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_status` (
  `psid` int NOT NULL COMMENT 'product status id',
  `pid` int NOT NULL,
  `price` double NOT NULL COMMENT 'Current price',
  `status` enum('pending','waiting','bidding','extended','broughtIn','success','canceled') NOT NULL COMMENT 'Current status\nShould be changed to enum',
  `end_time` datetime NOT NULL,
  PRIMARY KEY (`psid`),
  KEY `pid` (`pid`),
  CONSTRAINT `ps_pid` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='记录商品状态和价格，经常update';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_status`
--

LOCK TABLES `product_status` WRITE;
/*!40000 ALTER TABLE `product_status` DISABLE KEYS */;
INSERT INTO `product_status` VALUES (1,1,399,'bidding','2021-06-15 23:59:59'),(2,2,499,'bidding','2021-06-15 23:59:59'),(3,3,499,'bidding','2021-06-15 23:59:59'),(4,4,499,'bidding','2021-06-15 23:59:59'),(5,5,499,'bidding','2021-06-15 23:59:59'),(6,6,499,'bidding','2021-06-15 23:59:59'),(7,7,499,'bidding','2021-06-15 23:59:59'),(8,8,100,'waiting','2021-06-15 23:59:59'),(9,9,100,'waiting','2021-06-15 23:59:59'),(10,10,100,'waiting','2021-06-15 23:59:59'),(11,11,2000,'success','2021-04-12 00:00:00'),(12,12,3000,'success','2021-04-12 00:00:00'),(13,13,4000,'success','2021-04-12 00:00:00');
/*!40000 ALTER TABLE `product_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` enum('user','admin') NOT NULL DEFAULT 'user' COMMENT '0 for user, 1 for admin',
  `status` bit(1) NOT NULL DEFAULT b'0' COMMENT '0 for logout, 1 for login',
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Arthur','ABC123','user','\0','2021-04-29 09:06:57'),(2,'Ben','ABC123','user','\0','0000-00-00 00:00:00'),(3,'Cindy','ABC123','user','\0','0000-00-00 00:00:00'),(4,'Doug','ABC123','user','\0','0000-00-00 00:00:00'),(5,'Elen','ABC123','user','\0','0000-00-00 00:00:00'),(6,'Frank','ABC123','user','\0','0000-00-00 00:00:00'),(7,'Grace','ABC123','user','\0','0000-00-00 00:00:00'),(8,'Helen','ABC123','user','\0','0000-00-00 00:00:00'),(9,'Irwin','ABC123','admin','\0','0000-00-00 00:00:00'),(10,'Sophie','ABC123','user','\0','0000-00-00 00:00:00'),(11,'Vera','ABC123','user','\0','0000-00-00 00:00:00');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `uiid` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL,
  `portrait` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'https://pic1.zhimg.com/v2-1ec9c3e6ce311f8a39b096ccc22cd6a7_1440w.jpg?source=172ae18b' COMMENT '给个默认头像',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gender` enum('female','male','other','unknown') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'unknown',
  `birthday` date DEFAULT NULL,
  `regis_date` date NOT NULL,
  `balance` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`uiid`) USING BTREE,
  KEY `uid` (`uid`) USING BTREE,
  CONSTRAINT `user_info_fk1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,1,'https://lh3.googleusercontent.com/proxy/qca4NMUiES_kfRS1la8gnUrjh82BquLOhtAJbgoWgtD_zcJ7_P5hJ7QRMwiQs8ZIOcSMtLRFM6DlERb-08ttjdKUa_CfWX5NiUM7aNIrbnc34EEE4GeaMuO3SPp8Jw','9491234567','abc@uci.edu','male','1970-01-01','1970-01-01',100000),(2,2,'https://lh3.googleusercontent.com/proxy/qca4NMUiES_kfRS1la8gnUrjh82BquLOhtAJbgoWgtD_zcJ7_P5hJ7QRMwiQs8ZIOcSMtLRFM6DlERb-08ttjdKUa_CfWX5NiUM7aNIrbnc34EEE4GeaMuO3SPp8Jw','9491234567','abc@uci.edu','male',NULL,'2020-04-10',100),(3,3,'https://lh3.googleusercontent.com/proxy/qca4NMUiES_kfRS1la8gnUrjh82BquLOhtAJbgoWgtD_zcJ7_P5hJ7QRMwiQs8ZIOcSMtLRFM6DlERb-08ttjdKUa_CfWX5NiUM7aNIrbnc34EEE4GeaMuO3SPp8Jw','9491234567','abc@uci.edu','female',NULL,'2020-04-10',300),(4,4,'https://lh3.googleusercontent.com/proxy/qca4NMUiES_kfRS1la8gnUrjh82BquLOhtAJbgoWgtD_zcJ7_P5hJ7QRMwiQs8ZIOcSMtLRFM6DlERb-08ttjdKUa_CfWX5NiUM7aNIrbnc34EEE4GeaMuO3SPp8Jw','9491234567','abc@uci.edu','male',NULL,'2020-04-10',0),(5,5,'https://lh3.googleusercontent.com/proxy/qca4NMUiES_kfRS1la8gnUrjh82BquLOhtAJbgoWgtD_zcJ7_P5hJ7QRMwiQs8ZIOcSMtLRFM6DlERb-08ttjdKUa_CfWX5NiUM7aNIrbnc34EEE4GeaMuO3SPp8Jw','9491234567','abc@uci.edu','male',NULL,'2020-04-10',50),(6,6,'https://lh3.googleusercontent.com/proxy/qca4NMUiES_kfRS1la8gnUrjh82BquLOhtAJbgoWgtD_zcJ7_P5hJ7QRMwiQs8ZIOcSMtLRFM6DlERb-08ttjdKUa_CfWX5NiUM7aNIrbnc34EEE4GeaMuO3SPp8Jw','9491234567','abc@uci.edu','male',NULL,'2020-04-10',0),(7,7,'https://lh3.googleusercontent.com/proxy/qca4NMUiES_kfRS1la8gnUrjh82BquLOhtAJbgoWgtD_zcJ7_P5hJ7QRMwiQs8ZIOcSMtLRFM6DlERb-08ttjdKUa_CfWX5NiUM7aNIrbnc34EEE4GeaMuO3SPp8Jw','9491234567','abc@uci.edu','female',NULL,'2020-04-10',0),(8,8,'https://lh3.googleusercontent.com/proxy/qca4NMUiES_kfRS1la8gnUrjh82BquLOhtAJbgoWgtD_zcJ7_P5hJ7QRMwiQs8ZIOcSMtLRFM6DlERb-08ttjdKUa_CfWX5NiUM7aNIrbnc34EEE4GeaMuO3SPp8Jw','9491234567','abc@uci.edu','female',NULL,'2020-04-10',0),(9,9,'https://lh3.googleusercontent.com/proxy/qca4NMUiES_kfRS1la8gnUrjh82BquLOhtAJbgoWgtD_zcJ7_P5hJ7QRMwiQs8ZIOcSMtLRFM6DlERb-08ttjdKUa_CfWX5NiUM7aNIrbnc34EEE4GeaMuO3SPp8Jw','9491234567','abc@uci.edu','male',NULL,'2020-04-10',0),(10,10,'https://lh3.googleusercontent.com/proxy/qca4NMUiES_kfRS1la8gnUrjh82BquLOhtAJbgoWgtD_zcJ7_P5hJ7QRMwiQs8ZIOcSMtLRFM6DlERb-08ttjdKUa_CfWX5NiUM7aNIrbnc34EEE4GeaMuO3SPp8Jw','9491234567','abc@uci.edu','female',NULL,'2020-04-10',20000),(11,11,'https://lh3.googleusercontent.com/proxy/qca4NMUiES_kfRS1la8gnUrjh82BquLOhtAJbgoWgtD_zcJ7_P5hJ7QRMwiQs8ZIOcSMtLRFM6DlERb-08ttjdKUa_CfWX5NiUM7aNIrbnc34EEE4GeaMuO3SPp8Jw','9491234567','abc@uci.edu','female',NULL,'2020-04-12',300000);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist`
--

DROP TABLE IF EXISTS `wishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wishlist` (
  `wid` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL,
  `pid` int NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`wid`),
  KEY `wishlist_uid_idx` (`uid`),
  KEY `wishlist_createtime_idx` (`create_time` DESC),
  KEY `wishlist_pid_fk_idx` (`pid`),
  CONSTRAINT `wishlist_pid_fk` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`),
  CONSTRAINT `wishlist_uid_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist`
--

LOCK TABLES `wishlist` WRITE;
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
INSERT INTO `wishlist` VALUES (3,1,6,'2021-04-15 00:00:00'),(4,1,10,'2021-04-15 00:00:00'),(5,2,1,'2021-04-15 00:00:00'),(6,2,2,'2021-04-15 00:00:00'),(7,2,3,'2021-04-15 00:00:00'),(8,2,10,'2021-04-15 00:00:00'),(9,3,4,'2021-04-15 00:00:00'),(10,3,5,'2021-04-15 00:00:00'),(11,3,6,'2021-04-15 00:00:00'),(12,9,9,'2021-04-15 00:00:00'),(25,1,3,'2021-04-28 07:26:45');
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-29  2:16:08
