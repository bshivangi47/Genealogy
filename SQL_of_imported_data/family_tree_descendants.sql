-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: family_tree
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `descendants`
--

DROP TABLE IF EXISTS `descendants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `descendants` (
  `descendentId` int NOT NULL AUTO_INCREMENT,
  `parentId` int NOT NULL,
  `childId` int NOT NULL,
  PRIMARY KEY (`descendentId`),
  KEY `descendents_ibfk_1` (`parentId`),
  KEY `descendents_ibfk_2` (`childId`),
  CONSTRAINT `descendents_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `person` (`personId`),
  CONSTRAINT `descendents_ibfk_2` FOREIGN KEY (`childId`) REFERENCES `person` (`personId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descendants`
--

LOCK TABLES `descendants` WRITE;
/*!40000 ALTER TABLE `descendants` DISABLE KEYS */;
INSERT INTO `descendants` VALUES (1,4,1),(2,4,2),(3,4,3),(4,7,5),(5,7,6),(6,9,7),(7,9,8),(8,10,4),(9,10,9),(10,12,11),(11,13,9),(12,13,12),(13,16,15),(14,17,15),(15,15,10),(16,14,10);
/*!40000 ALTER TABLE `descendants` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-14 22:40:33
