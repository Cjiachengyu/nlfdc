CREATE DATABASE  IF NOT EXISTS `nlfdc` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `nlfdc`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: nlfdc
-- ------------------------------------------------------
-- Server version	5.6.19

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

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `articleId` int(11) NOT NULL AUTO_INCREMENT,
  `firstMenuId` int(2) NOT NULL,
  `secondMenuId` int(4) NOT NULL,
  `creatorId` int(3) NOT NULL,
  `createTime` int(11) NOT NULL,
  `content` text NOT NULL,
  `isDeleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`articleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `first_menu`
--

DROP TABLE IF EXISTS `first_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `first_menu` (
  `firstMenuId` int(2) NOT NULL AUTO_INCREMENT,
  `firstMenuName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`firstMenuId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `first_menu`
--

LOCK TABLES `first_menu` WRITE;
/*!40000 ALTER TABLE `first_menu` DISABLE KEYS */;
INSERT INTO `first_menu` VALUES (1,'首页'),(2,'政务公开'),(3,'住房保障'),(4,'房屋登记'),(5,'维修资金'),(6,'物业管理'),(7,'网上备案'),(8,'信息中心'),(9,'监督执法'),(10,'诚信档案');
/*!40000 ALTER TABLE `first_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `second_menu`
--

DROP TABLE IF EXISTS `second_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `second_menu` (
  `secondMenuId` int(4) NOT NULL AUTO_INCREMENT,
  `secondMenuName` varchar(45) DEFAULT NULL,
  `firstMenuId` int(2) unsigned DEFAULT '0',
  PRIMARY KEY (`secondMenuId`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `second_menu`
--

LOCK TABLES `second_menu` WRITE;
/*!40000 ALTER TABLE `second_menu` DISABLE KEYS */;
INSERT INTO `second_menu` VALUES (1,'单位简介',2),(2,'工作职责',2),(3,'文件通知',2),(4,'公示公告',2),(5,'政策法规',2),(6,'办事指南',3),(7,'建设信息',3),(8,'分配信息',3),(9,'动态管理',3),(10,'政策法规',3),(11,'办事指南',4),(12,'业务介绍',4),(13,'登记类别',4),(14,'办事指南',5),(15,'账户查询',5),(16,'政策法规',5),(17,'公告通知',6),(18,'政策法规',6),(19,'年检情况',6),(20,'网上查询',7),(21,'新闻动态',8),(22,'通知公告',8),(23,'政策法规',8),(24,'便民指南',8),(25,'信息公开',8),(26,'综合动态',9),(27,'机构职能',9),(28,'政策法规',9),(29,'通知公告',9),(30,'开发企业',10),(31,'中介机构',10),(32,'物业管理',10),(33,'估价机构',10),(34,'销售公告',10),(35,'住房保障',10),(36,'在线投诉',10);
/*!40000 ALTER TABLE `second_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userId` int(3) NOT NULL AUTO_INCREMENT,
  `loginName` varchar(45) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `userRole` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visit_count`
--

DROP TABLE IF EXISTS `visit_count`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visit_count` (
  `day` int(8) NOT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visit_count`
--

LOCK TABLES `visit_count` WRITE;
/*!40000 ALTER TABLE `visit_count` DISABLE KEYS */;
INSERT INTO `visit_count` VALUES (20150819,4),(20150820,3);
/*!40000 ALTER TABLE `visit_count` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-08-21  7:02:15
