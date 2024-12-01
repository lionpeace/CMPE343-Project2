-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: firmms
-- ------------------------------------------------------
-- Server version	9.1.0

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
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `employee_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `name` varchar(150) NOT NULL,
  `surname` varchar(150) NOT NULL,
  `phone_no` varchar(40) DEFAULT NULL,
  `dateofbirth` date NOT NULL,
  `dateofstart` date NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `role` int NOT NULL,
  `new_user` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`employee_id`),
  KEY `role` (`role`),
  CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`role`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'bariscan','khas1234','Barış Can','Aslan','555-1234','1980-01-01','2020-01-01','jdoe@example.com',1,0),(2,'alicesmith','khas1234','Alice','Smith','5364356754','1985-02-02','2021-02-02','alicesmith@gmail.com',9,1),(3,'bwhite','khas1234','Bob','White','555-8765','1990-03-03','2022-03-03','bwhite@example.com',2,1),(6,'emartin','khas1234','Emma','Martin','555-9876','1987-06-06','2021-06-06','emartin@example.com',4,1),(7,'flee','khas1234','Frank','Lee','555-5432','1992-07-07','2022-07-07','flee@example.com',3,1),(8,'gking','khas1234','Grace','King','555-6543','1997-08-08','2023-08-08','gking@example.com',2,1),(9,'henryoung','Khas.123','Henry Matheew','Younger','0538 359 9269','2003-03-02','2020-09-09','henryoung@gmail.com',1,0),(10,'yarenbulut','Kadirhas.123','Yaren','Bulut','537 456 75 34','2000-06-22','2024-11-30',NULL,2,0),(11,'zisantunceli','khas1234','Zişan','Tunçeli',NULL,'2003-03-03','2024-06-22',NULL,5,1);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-01  3:25:11
