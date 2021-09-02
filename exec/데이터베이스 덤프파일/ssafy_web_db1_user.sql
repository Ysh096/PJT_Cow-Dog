-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ssafy_web_db1
-- ------------------------------------------------------
-- Server version	5.7.33-log

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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `department` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'sdf','sdf','sdf','sdf','sdf'),(2,'asdf','wfw','$2a$10$M3IK52JsdvAJM5P8IP2TE.5vdwythIc3qzUAtro9/rZ32JcsM89Ze','asdf',NULL),(3,'asdf','asdfwf','$2a$10$GIzrPP3YlOgYCqypgx2kOem4uA9weXVURp4mGNYah0b4NCiSXcEy2','asdf',NULL),(4,'asdf','asdfwf','$2a$10$Oe8eiS8OjivJ6kaACOkYzuukD1LNp9vQfXivcB7AWiVCkUWMnzRve','asdf',NULL),(5,'sadf','sdfsf','$2a$10$ogoxMbK2v1wJ9p/iV163yOnmTmsRFz8N7i/.dnQ6tZPtjniQetrXi','wfsaf',NULL),(6,'asdfsaf','dfdfasdfasdf','$2a$10$BPstd2DJ0inRSpJqo7XQ5eKyy6QrD5bv0i4X497zAzKUN22iprUBi','sdfasdfs',NULL),(7,'asdfsaf','dfdfasdfasdf','$2a$10$3D/rTf6baEj5aTrlsVAHH.pa04dh6YkotkGZnDgdRUibkI/EmH7KC','sdfasdfs',NULL),(8,'dfa','fasdfas','$2a$10$bl6CAhiBOJD/gqRcCLkWUeJ13ILF/RYXJ/1KFUdyaxC9okxnW.VlC','sdfas',NULL),(9,'asdf','dggwgwg','$2a$10$5DdRxqHMIfH3UH5cvu5FbufAmm0NvQ.5XpWbrvsrHgIME.O1fHulK','wfeas',NULL),(10,'sdf','dfasdfasdf','$2a$10$1foeoSj2UGppnbPfmxa7q.wP7x6xOvbhUU/pLu5F.DqBY66tGjqDC','asdfas',NULL),(11,'sadf','김정운','$2a$10$xnPSvrtdMUXZUP.zZq7fOugLR6eS8QEb6/sSwpEZUzkNsWJlNNK8S','wfwf',NULL),(12,'fwww','김정운','$2a$10$MSscvhxcs9sAoRJPVrIYRegolizWNwawm3LgcZUrh4QK5CNE2Ihlu','fwfwfw',NULL),(13,'SSAFY','김정운','$2a$10$lIfq7KlcCjvbN2qtEI3mauAoSt92WGpBCB4NQQVTR2YCD0zMLBCge','학생','rlawjddns0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-20  7:12:36
