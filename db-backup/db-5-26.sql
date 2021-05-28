-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: alwayswin.cdiknsvsllhn.us-east-1.rds.amazonaws.com    Database: alwayswin
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
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,1,'Arthur','110','500 Campus Dr','CA','00000'),(2,1,'Arthur\'s father','120','NYPD','NY','888888'),(3,2,'Ben','9491234567','200 Campus Dr','CA','92612'),(5,3,'Cindy','9491234567','500 Campus Dr','CA','92612'),(6,4,'Doug','9491234567','500 Campus Dr','CA','92612'),(8,6,'Father','9491234567','500 Campus Dr','CA','92612'),(9,7,'Grace','9491234567','500 Campus Dr','CA','92612'),(10,8,'Helen','9491234567','500 Campus Dr','CA','92612'),(11,9,'Vera','9491234567','500 Campus Dr','CA','92612'),(82,1,'xiaohong','120','NYPD','NY','99999'),(83,1,'xiaohong','120','NYPD','NY','99999'),(84,1,'Arthur\'','120','NYPD','NY','888888'),(86,1,'Amy','110','police office','CA','92615'),(89,10,'Sophie','9498670584','646 Stanford Ct','CA','92612'),(90,10,'Husky','123456','INTERNATIONAL CENTER G302 UCI STUDENT CENTER','AK','10101');
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
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='出价表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bidding`
--

LOCK TABLES `bidding` WRITE;
/*!40000 ALTER TABLE `bidding` DISABLE KEYS */;
INSERT INTO `bidding` VALUES (1,11,2,1500,'2021-04-11 00:00:00'),(2,11,1,2000,'2021-04-12 00:00:00'),(3,12,1,3000,'2021-04-12 00:00:00'),(4,13,9,4000,'2021-04-12 00:00:00'),(16,3,2,550,'2021-05-12 03:37:18'),(17,3,2,600,'2021-05-13 23:15:11');
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
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `figure`
--

LOCK TABLES `figure` WRITE;
/*!40000 ALTER TABLE `figure` DISABLE KEYS */;
INSERT INTO `figure` VALUES (1,1,'https://alwayswin-figures.s3.amazonaws.com/product-figure/s-l501.jpg','this is a figure','','2021-05-12 04:33:05'),(2,2,'https://alwayswin-figures.s3.amazonaws.com/product-figure/s-l501.jpg','black','','2021-04-15 00:00:00'),(3,3,'https://alwayswin-figures.s3.amazonaws.com/product-figure/iphxr-bl.png','blue','','2021-04-15 00:00:00'),(4,4,'https://alwayswin-figures.s3.amazonaws.com/product-figure/iphxr-bl.png','blue','','2021-04-15 00:00:00'),(5,5,'https://alwayswin-figures.s3.amazonaws.com/product-figure/iphxr-bl.png','blue','','2021-04-15 00:00:00'),(6,6,'https://alwayswin-figures.s3.amazonaws.com/product-figure/s-l502.jpg','orange','','2021-04-15 00:00:00'),(7,7,'https://alwayswin-figures.s3.amazonaws.com/product-figure/iphxr-bl.png','orange','','2021-04-15 00:00:00'),(8,8,'https://alwayswin-figures.s3.amazonaws.com/product-figure/s-l500.jpg',NULL,'','2021-04-15 00:00:00'),(9,9,'https://alwayswin-figures.s3.amazonaws.com/product-figure/Bolt+Drone.jpg',NULL,'','2021-04-15 00:00:00'),(10,10,'https://alwayswin-figures.s3.amazonaws.com/product-figure/s-l500.jpg','aaaaaaa','','2021-04-15 00:00:00'),(11,1,'https://alwayswin-figures.s3.amazonaws.com/product-figure/s-l640.jpg','fake','\0','2021-04-15 00:00:00'),(12,2,'https://alwayswin-figures.s3.amazonaws.com/product-figure/s-l640.jpg','fake','\0','2021-04-15 00:00:00'),(69,50,'https://alwayswin-figures.s3.amazonaws.com/product-figure/s-l502.jpg','default picture.','','2021-05-13 02:40:14'),(70,51,'https://alwayswin-figures.s3.amazonaws.com/product-figure/Bolt+Drone.jpg','default picture.','','2021-05-13 16:24:53'),(71,11,'https://alwayswin-figures.s3.amazonaws.com/product-figure/default-product-thumbnail.png','','','2021-05-13 23:42:38'),(72,12,'https://alwayswin-figures.s3.amazonaws.com/product-figure/default-product-thumbnail.png','','','2021-05-13 23:42:42'),(73,13,'https://alwayswin-figures.s3.amazonaws.com/product-figure/default-product-thumbnail.png','','\0','2021-05-13 23:42:58'),(78,54,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','default picture.','','2021-05-15 02:13:30'),(92,68,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','default picture.','','2021-05-26 16:10:08'),(93,69,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','default picture.','','2021-05-26 18:23:36'),(94,70,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','default picture.','','2021-05-26 18:24:53'),(95,71,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','default picture.','','2021-05-26 18:55:41'),(96,72,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','default picture.','','2021-05-26 18:58:20'),(97,73,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','default picture.','','2021-05-26 19:01:18'),(98,13,'https://alwayswin-figures.s3.amazonaws.com/product-figure/default-product-thumbnail.png','','\0','2021-05-27 01:13:53'),(99,13,'https://alwayswin-figures.s3.amazonaws.com/product-figure/default-product-thumbnail.png','','\0','2021-05-27 01:13:57'),(101,74,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','default picture.','','2021-05-27 01:34:44'),(102,75,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','default picture.','','2021-05-27 01:35:06'),(103,76,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','default picture.','','2021-05-27 10:49:03');
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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'m0aCrbDA',1,11,'heaven front door',2000,'2021-04-12 00:00:00','shipped'),(2,'ylKmNUWJ',1,12,'Campus Dr',3000,'2021-04-12 00:00:00','paid'),(3,'hMQJhDJw',9,13,'Campus Dr',4000,'2021-04-12 00:00:00','shipped'),(4,'L2Rqvp14',3,4,'Campus Dr',202,'2021-04-15 00:00:00','paid');
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
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,1,'Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included. This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone is fully functional with all carrier networks. It does not come with a SIM card, you will need to get one from your carrier of choice.','cell phone','','','2021-04-15 00:00:00','2021-05-29 00:00:00','2021-06-09 23:59:59',399,599,499,20,'','\0'),(2,1,'Apple iPhone XR 256GB Factory Unlocked','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included.','cell phone',NULL,NULL,'2021-04-10 00:00:00','2021-04-15 00:00:00','2021-06-14 23:59:59',499,699,599,30,'','\0'),(3,10,'Apple iPhone 7 256GB Factory Unlocked Smartphone 4G LTE iOS Smartphone','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included.','cell phone',NULL,NULL,'2021-04-10 00:00:00','2021-03-04 00:00:00','2021-05-24 23:43:59',499,699,599,30,'','\0'),(4,2,'Apple iPhone 8 256GB Factory Unlocked Smartphone 4G LTE iOS Smartphone','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included.','cell phone',NULL,NULL,'2021-04-10 00:00:00','2021-04-15 00:00:00','2021-06-15 23:59:59',499,699,599,30,'','\0'),(5,2,'Apple iPhone X 256GB Factory Unlocked Smartphone 4G LTE iOS Smartphone','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included.','cell phone',NULL,NULL,'2021-04-10 00:00:00','2021-04-15 00:00:00','2021-06-15 23:59:59',499,699,599,30,'','\0'),(6,3,'Apple iPhone XS 256GB Factory Unlocked Smartphone 4G LTE iOS Smartphone','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included.','cell phone',NULL,NULL,'2021-04-10 00:00:00','2021-04-15 00:00:00','2021-06-15 23:59:59',499,699,599,30,'','\0'),(7,4,'Apple iPhone 11 256GB Factory Unlocked Smartphone 4G LTE iOS Smartphone','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included.','cell phone',NULL,NULL,'2021-04-10 00:00:00','2021-04-15 00:00:00','2021-06-15 23:59:59',499,699,599,30,'','\0'),(8,1,'Bolt Drone FPV Racing Drone Carbon Fiber with First Person View Goggles 5.8 Ghz','Only have flown this drone 1 time. Flew great and fast! Around 30 mph. I have since bought other drones so I no longer would need to keep this one around','camera','','','2021-04-15 00:00:00','2021-05-30 00:00:00','2021-06-15 23:59:59',100,400,150,10,'',''),(9,2,'Bolt Drone FPV Racing Drone Carbon Fiber with First Person View Goggles 5.8 Ghz','Only have flown this drone 1 time. Flew great and fast! Around 30 mph. I have since bought other drones so I no longer would need to keep this one around','camera',NULL,NULL,'2021-04-15 00:00:00','2021-04-16 00:00:00','2021-06-15 23:59:59',100,300,150,10,'','\0'),(10,10,'Bolt Drone FPV Racing Drone Carbon Fiber with First Person View Goggles 5.8 Ghz','Only have flown this drone 1 time. Flew great and fast! Around 30 mph. I have since bought other drones so I no longer would need to keep this one around','camera',NULL,NULL,'2021-04-15 00:00:00','2021-04-16 00:00:00','2021-06-15 23:59:59',100,300,150,10,'','\0'),(11,2,'Play Station 1000','SOLD!','gaming console',NULL,NULL,'2021-04-10 00:00:00','2021-04-10 00:00:00','2021-06-15 23:59:59',1000,10000,NULL,10,'','\0'),(12,2,'Play Station 2000','SOLD!','gaming console',NULL,NULL,'2021-04-10 00:00:00','2021-04-10 00:00:00','2021-06-15 23:59:59',2000,20000,NULL,10,'','\0'),(13,2,'Play Station 3000','SOLD!','gaming console',NULL,NULL,'2021-04-10 00:00:00','2021-04-10 00:00:00','2021-06-15 23:59:59',3000,30000,NULL,10,'','\0'),(50,4,'Bolt Drone FPV Racing Drone Carbon Fiber with First Person View Goggles 5.8 Ghz','Only have flown this drone 1 time. Flew great and fast! Around 30 mph. I have since bought other drones so I no longer would need to keep this one around','camera','','','2021-05-13 02:40:13','2021-05-30 00:00:00','2021-06-15 23:59:59',100,400,150,10,'\0','\0'),(51,4,'Bolt Drone FPV Racing Drone Carbon Fiber with First Person View Goggles 5.8 Ghz','Only have flown this drone 1 time. Flew great and fast! Around 30 mph. I have since bought other drones so I no longer would need to keep this one around','camera','','','2021-05-13 16:24:52','2021-05-24 23:53:50','2021-06-15 23:59:59',100,400,150,10,'\0','\0'),(54,4,'Apple iPhone 11 256GB Factory Unlocked Smartphone 4G LTE iOS Smartphone','This Apple iPhone XR 64GB Factory Unlocked Smartphone 4G LTE iOS Smartphone has been determined fully functional by our industry leading functionality inspection. It will show signs of wear like scratches, scuffs, and minor nicks on the screen or body. It will NOT come in its original packaging but will include an MFI certified lightning cable and UL approved power adapter. Headsets, SIM card and manual are NOT included.','cell phone','','','2021-05-15 02:13:29','2021-05-16 00:00:00','2021-06-15 23:59:59',499,699,599,30,'\0','\0'),(68,11,'TEST','TEST','accessory','','','2021-05-26 16:10:08','2021-05-04 00:00:00','2021-05-31 00:00:00',1,999,0,1,'\0','\0'),(69,11,'TEST','TEST','camera','','','2021-05-26 18:23:36','2021-05-17 00:00:00','2021-06-05 00:00:00',1,9999,0,1,'\0','\0'),(70,11,'TEST2','TEST','camera','','','2021-05-26 18:24:53','2021-05-17 00:00:00','2021-06-05 00:00:00',1,9999,0,1,'\0','\0'),(71,4,'Nikon 24-120mm f/4G ED VR AF-S NIKKOR Lens','Nikon NIKKOR f/4 lenses are advanced and compact lenses that deliver professional image quality at any aperture or focal length. This high-end 24-120 mm Nikon lens is packed with helpful technology. The built-in vibration stabilization VR II enables handheld shooting to be pushed to a new level, providing flexibility and range for active photographers. The advanced silent wave motor, or SWM, is ideal for shooting events with an emphasis on spontaneous captures and smooth, silent auto-focusing features. The nano crystal coat developed by Nikon eliminates internal lens reflections, and the extra-low dispersion glass provides optimum correction of chromatic aberrations. This Nikon product is a key lens for any professional photographer who wants to capture life-like and breathtaking photographs. This Nikon NIKKOR 24-120 mm f/4 AS G SWM AF-S VR IF N M/A ED lens is compact as well as versatile. It is perfect for shooting landscapes, portraits, parties, and especially distant subjects with your cameras. This Nikon lens offers a constant maximum aperture in order to maintain exposure settings throughout the entire zoom range. Its angle view is equivalent to a focal length of 36-180 mm and enables handheld shooting of up to four amazing shutter speeds. The angle of view of these Nikon NIKKOR 24-120 mm f/4 AS G SWM AF-S VR IF N M/A ED lenses features vibration reduction to improve the function of your camera. They are also specifically engineered for VR NIKKOR lenses. This enables handheld shooting at up to four shutter speeds, slower than would normally be possible. This makes it possible to capture sharper video and images every time. ','camera','','','2021-05-26 18:55:40','2021-05-30 00:00:00','2021-06-02 18:55:40',200,800,600,50,'','\0'),(72,2,'DJI Mavic Air - Red Drone - 4K Camera Portable Compact','The Ultraportable Mavic Air features high-end flight performance and functionality for limitless exploration. ','camera','','','2021-05-26 18:58:20','2021-05-30 00:00:00','2021-06-02 18:58:20',200,1200,800,50,'','\0'),(73,2,'DJI Inspire 2 Quadcopter (DJI Refurbished)','There are no better experts in DJI products than those at DJI. Every refurbished product has undergone a professional refurbishing process that brings it up to the same high standards as brand new products, and all refurbished products come with new parts, new packaging and DJI\'s standard Manufacturer Warranty. If you are not happy with a refurbished product and have not yet activated it, you can return it within seven days of receipt for a full refund or a replacement refurbished product. ','camera','','','2021-05-26 19:01:18','2021-05-30 00:00:00','2021-06-02 19:01:18',200,2700,1800,50,'','\0'),(74,11,'TEST FIGURE','TEST FIGURE','cell phone','','','2021-05-27 01:34:44','2021-05-28 01:34:44','2021-06-03 00:00:00',1,1111,0,1,'','\0'),(75,11,'TEST FIGURE','TEST FIGURE','cell phone','','','2021-05-27 01:35:06','2021-05-28 01:35:06','2021-06-03 00:00:00',1,1111,0,1,'','\0'),(76,11,'TEST FIGURE','TESET FIGURE','tablet','','','2021-05-27 10:49:03','2021-05-28 10:49:03','2021-05-29 00:00:00',1,999,0,1,'','\0');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `product_preview`
--

DROP TABLE IF EXISTS `product_preview`;
/*!50001 DROP VIEW IF EXISTS `product_preview`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `product_preview` AS SELECT 
 1 AS `pid`,
 1 AS `uid`,
 1 AS `title`,
 1 AS `cate_1`,
 1 AS `start_time`,
 1 AS `end_time`,
 1 AS `auto_win_price`,
 1 AS `price`,
 1 AS `status`,
 1 AS `url`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `product_status`
--

DROP TABLE IF EXISTS `product_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_status` (
  `psid` int NOT NULL AUTO_INCREMENT COMMENT 'product status id',
  `pid` int NOT NULL,
  `price` double NOT NULL COMMENT 'Current price',
  `status` enum('pending','waiting','bidding','extended1','extended2','extended3','broughtIn','success','canceled') NOT NULL COMMENT 'Current status\nShould be changed to enum',
  `end_time` datetime NOT NULL,
  PRIMARY KEY (`psid`),
  UNIQUE KEY `pid_UNIQUE` (`pid`),
  KEY `pid` (`pid`),
  CONSTRAINT `ps_pid` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='记录商品状态和价格，经常update';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_status`
--

LOCK TABLES `product_status` WRITE;
/*!40000 ALTER TABLE `product_status` DISABLE KEYS */;
INSERT INTO `product_status` VALUES (1,1,399,'waiting','2021-06-15 23:59:59'),(2,2,718,'success','2021-06-15 23:59:59'),(3,3,600,'success','2021-05-24 23:56:00'),(4,4,730,'success','2021-06-15 23:59:59'),(5,5,720,'success','2021-06-15 23:59:59'),(6,6,720,'success','2021-06-15 23:59:59'),(7,7,710,'success','2021-06-15 23:59:59'),(8,8,100,'canceled','2021-05-24 22:33:50'),(9,9,100,'broughtIn','2021-05-24 18:10:00'),(10,10,500,'success','2021-06-15 23:59:59'),(11,11,2000,'success','2021-04-12 00:00:00'),(12,12,3000,'success','2021-04-12 00:00:00'),(13,13,4000,'success','2021-04-12 00:00:00'),(43,50,100,'waiting','2021-06-15 23:59:59'),(44,51,30,'bidding','2021-06-15 23:59:59'),(45,54,499,'bidding','2021-06-15 23:59:59'),(59,68,1,'pending','2021-05-31 00:00:00'),(60,69,1,'pending','2021-06-05 00:00:00'),(61,70,1,'pending','2021-06-05 00:00:00'),(62,71,200,'pending','2021-06-02 18:55:40'),(63,72,200,'pending','2021-06-02 18:58:20'),(64,73,200,'pending','2021-06-02 19:01:18'),(65,74,1,'pending','2021-06-03 00:00:00'),(66,75,1,'pending','2021-06-03 00:00:00'),(67,76,1,'pending','2021-05-29 00:00:00');
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
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'COLLATE SQL_Latin1_General_CP1_CS_AS to make it case sensitive',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` enum('user','admin') CHARACTER SET utf8 NOT NULL DEFAULT 'user' COMMENT '0 for user, 1 for admin',
  `status` bit(1) NOT NULL DEFAULT b'0' COMMENT '0 for logout, 1 for login',
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=latin1 COLLATE=latin1_general_cs ROW_FORMAT=COMPACT COMMENT='uid 1-57, passwords are all "ABC123"';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Arthur','$2a$10$ULhRvLmZcQas6NY4gQfO4OEav29zJG7kfJtDxkMPjBePzWPnlXRP2','user','','2021-05-27 05:57:13'),(2,'Ben','$2a$10$ZEesco2ZAahlWDDw0JT75e3wX0miwUoDWFrqkBscQMjz1WTFx1IbW','user','\0','2021-05-27 05:29:13'),(3,'Cindy','$2a$10$1//IskP8tjmc2WcefVZDdOmooKDR8oHVO3SVdAuxgl4hiCijgX6cq','user','','2021-04-29 22:55:29'),(4,'Doug','$2a$10$ZLUfOjtdf1bjD6bcT1JhWuIFZgOOenMJg93i7VP4hmDdXnPiMCDfC','user','','2021-04-29 22:56:44'),(5,'Elen','$2a$10$oL.daIun9NvbFGWPg1y1c.e7krqC7LaHClOgEnn4.oJqS4YkzLN.i','user','','2021-04-29 22:58:08'),(6,'Frank','$2a$10$GUUW9sVAIc5GnDSM1Qg6weWvW1IcLB7ZmyFfpxcemwb3O/cEKqQ.S','user','','2021-04-29 22:59:32'),(7,'Grace','$2a$10$afVEI4tWgjsEgScrbRWT.eEL2AqTIdvnLebfSku/Op3ua9dbrT5NK','user','','2021-04-29 22:59:48'),(8,'Helen','$2a$10$1dfgZ0ACESVnLnPyajyeTuq2J7uanDVK.5AFu2JWlBWsfze4VciDy','user','\0','2021-04-29 23:52:44'),(9,'Irwin','$2a$10$99iIaUGGHm76lEl7ffxNxOlGKZIZdojj6kImalpzwr/D.jwCk962G','admin','','2021-04-29 23:01:00'),(10,'Sophie','$2a$10$QjohEqwt0NKc9n0k0lNSG.Jpa8WqB4ve/d681lQkx/R67IGfi3yfy','user','\0','2021-05-27 05:55:20'),(11,'Vera','$2a$10$zXtRUgZexqaOZBqmYlNMvuDy8PrmPjDWhXTxCiBkPu13cgaN8yCZK','user','','2021-05-27 10:48:41'),(58,'KillerQueen','$2a$10$fir9/E9lLWsrpBVGWTsD2uhFjgND9pSyq2NM8sPddDUSJVOZTrp8C','user','','2021-04-29 22:43:50'),(87,'Jason123456','$2a$10$m3rON7EkHBUMilNYgOWMf.DtR38Lqvxxu8g99ehb9B12NoJBXODBe','user','\0','1969-12-31 16:00:00'),(95,'Lilian','$2a$10$Bjcjek753iDpFQYP81R3PORruTOuAaWNxc1KjBtqZlcc3cZTmzGLm','user','\0','2021-05-23 02:59:34'),(96,'Mingming','$2a$10$IyZobDOcp.hz/1U8qhLKEON/w6m/ymzmskm27U7to93QaOZrTyaR2','user','','2021-05-24 23:00:04');
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
  `portrait` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png' COMMENT '给个默认头像',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gender` enum('female','male','other','unknown') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'unknown',
  `birthday` date DEFAULT NULL,
  `regis_date` date NOT NULL,
  `balance` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`uiid`) USING BTREE,
  UNIQUE KEY `uid_UNIQUE` (`uid`),
  KEY `uid` (`uid`) USING BTREE,
  CONSTRAINT `user_info_fk1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,1,'https://alwayswin-figures.s3.amazonaws.com/icon/arthur.jpg','9491234567','abc@uci.edu','male','1970-01-01','1970-01-01',101000),(2,2,'https://alwayswin-figures.s3.amazonaws.com/icon/pickle.png','9499490123','abc@uci.edu','other','1998-01-20','2020-04-10',200),(3,3,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','9491234567','abc@uci.edu','female',NULL,'2020-04-10',300),(4,4,'https://alwayswin-figures.s3.amazonaws.com/icon/doug.PNG','9491234567','abc@uci.edu','male',NULL,'2020-04-10',0),(5,5,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','9491234567','abc@uci.edu','male',NULL,'2020-04-10',50),(6,6,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','9491234567','abc@uci.edu','male',NULL,'2020-04-10',0),(7,7,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','9491234567','abc@uci.edu','female',NULL,'2020-04-10',0),(8,8,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','9491234567','abc@uci.edu','female',NULL,'2020-04-10',0),(9,9,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','9491234567','abc@uci.edu','male',NULL,'2020-04-10',0),(10,10,'https://alwayswin-figures.s3.amazonaws.com/icon/sophie.jpg','110','shuqiny2@uci.edu','female','1999-01-01','2020-04-10',910),(11,11,'https://alwayswin-figures.s3.amazonaws.com/icon/vera.jpg','9491234567','abc@uci.edu','female','1970-01-30','2020-04-12',300000),(21,58,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png','7654321','hahaha@gmail.com','other','2020-10-10','2021-04-29',200),(35,87,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png',NULL,NULL,'unknown',NULL,'2021-05-14',0),(40,95,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png',NULL,NULL,'unknown',NULL,'2021-05-23',0),(41,96,'https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png',NULL,NULL,'unknown',NULL,'2021-05-25',0);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `user_preview`
--

DROP TABLE IF EXISTS `user_preview`;
/*!50001 DROP VIEW IF EXISTS `user_preview`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `user_preview` AS SELECT 
 1 AS `uid`,
 1 AS `username`,
 1 AS `role`,
 1 AS `portrait`*/;
SET character_set_client = @saved_cs_client;

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
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist`
--

LOCK TABLES `wishlist` WRITE;
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
INSERT INTO `wishlist` VALUES (5,2,1,'2021-04-15 00:00:00'),(7,2,3,'2021-04-15 00:00:00'),(55,3,6,'2021-05-10 18:36:31'),(65,4,5,'2021-05-10 21:25:28'),(68,4,6,'2021-05-10 21:44:57'),(69,4,3,'2021-05-10 21:46:42'),(70,2,7,'2021-05-10 21:46:42'),(71,3,7,'2021-05-10 21:47:58'),(78,58,4,'2021-05-15 02:13:23'),(80,2,2,'2021-05-15 02:13:32'),(121,1,1,'2021-05-26 16:02:59'),(126,10,2,'2021-05-26 19:30:21'),(128,10,54,'2021-05-26 19:30:30'),(129,10,1,'2021-05-26 19:33:25');
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `product_preview`
--

/*!50001 DROP VIEW IF EXISTS `product_preview`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`admin`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `product_preview` AS select `product`.`pid` AS `pid`,`product`.`uid` AS `uid`,`product`.`title` AS `title`,`product`.`cate_1` AS `cate_1`,`product`.`start_time` AS `start_time`,`product_status`.`end_time` AS `end_time`,`product`.`auto_win_price` AS `auto_win_price`,`product_status`.`price` AS `price`,`product_status`.`status` AS `status`,`figure`.`url` AS `url` from ((`product` join `product_status` on((`product`.`pid` = `product_status`.`pid`))) join `figure` on(((`product`.`pid` = `figure`.`pid`) and (`figure`.`is_thumbnail` = 1)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `user_preview`
--

/*!50001 DROP VIEW IF EXISTS `user_preview`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`admin`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `user_preview` AS select `user`.`uid` AS `uid`,`user`.`username` AS `username`,`user`.`role` AS `role`,`user_info`.`portrait` AS `portrait` from (`user` join `user_info` on((`user`.`uid` = `user_info`.`uid`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-27 14:22:01
