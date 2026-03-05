-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: campnews
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Database structure for table `campnews`
--
DROP DATABASE IF EXISTS `campnews`;
CREATE DATABASE `campnews`;
USE `campnews`;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `text` varchar(500) NOT NULL,
                           `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                           `post_id` int NOT NULL,
                           `user_id` int NOT NULL,
                           PRIMARY KEY (`id`),
                           KEY `fk_comment_post` (`post_id`),
                           KEY `fk_comment_user` (`user_id`),
                           CONSTRAINT `fk_comment_post` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
                           CONSTRAINT `fk_comment_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'Changed my Text now haha!','2025-04-24 07:05:44',3,2),(2,'My Comment for Post 3','2025-04-24 07:05:44',3,2),(4,'testli','2025-04-24 11:30:34',3,8),(5,'hier auch ein test haha #hebuhans','2025-04-24 12:03:16',5,8);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `text` varchar(255) NOT NULL,
                        `title` varchar(255) NOT NULL,
                        `user_id` int DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        KEY `FK72mt33dhhs48hf9gcqrq4fxte` (`user_id`),
                        CONSTRAINT `FK72mt33dhhs48hf9gcqrq4fxte` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (3,'Mein Cooler Text','Mein Cooler Post',1),(4,'Mein Cooler Text','Mein Cooler Post',1),(5,'Mein Cooler Text','Mein Cooler Post',1),(6,'This is an updated post.','Updated Post',3),(7,'Mein cooler Post','Dieser Post wurde mit Frontend erstellt',7),(9,'Updated','Updated',8);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_tag`
--

DROP TABLE IF EXISTS `post_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_tag` (
                            `post_id` int NOT NULL,
                            `tag_id` int NOT NULL,
                            PRIMARY KEY (`post_id`,`tag_id`),
                            KEY `FKac1wdchd2pnur3fl225obmlg0` (`tag_id`),
                            CONSTRAINT `FKac1wdchd2pnur3fl225obmlg0` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`),
                            CONSTRAINT `FKc2auetuvsec0k566l0eyvr9cs` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_tag`
--

LOCK TABLES `post_tag` WRITE;
/*!40000 ALTER TABLE `post_tag` DISABLE KEYS */;
INSERT INTO `post_tag` VALUES (3,1),(4,1),(5,1),(6,1),(7,1),(9,1);
/*!40000 ALTER TABLE `post_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
                       `id` int NOT NULL AUTO_INCREMENT,
                       `name` varchar(255) NOT NULL,
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,'Spannend'),(2,'Langweilig');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `username` varchar(255) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','$2a$10$KWQ7x7SKjiZrb3iJk4rCzOtVBAji56uqVJZ1N5447/TBi7GycREuS'),(2,'test1','$2a$10$l59l1AJOJQKR3lIpJMXPp.D5u3HTvyGbWpSPAOEO8wqs3rpMaqv9q'),(3,'test2','$2a$10$ilyMczIFUeLJH2rpGHM9Z.mhXcsNPk9qBB5hLRQTBoAcS9.yB04j2'),(5,'newuser','$2a$10$Xx6dHDBT9k0HZllLkMFyieByTmqIFDBpvw7LBVtgH8PJh/xjVeuvS'),(6,'mike','$2a$10$oJurx7uRzz4/GmGamDnNOe7Bgv4lrljtEEUnuH0akP049DXL0Je4G'),(7,'hebuhans','$2a$10$LYItJs1.5r5UslM8Hji1IOCqkqpPHniUU2EA8ii4RBiWkkZuZL5Ne'),(8,'beckmi','$2a$10$zshTJlRKEy6z/H8Oyyh02.Egos78kXgo90wHNqhZ9LVTTebDF8DBq'),(9,'mischi@mischitest.ch','$2a$10$Bl1wyHSwSf5EkHR3r0VpCOyrcQ5o6Rg9hexJzF.go4lXwAHpuLP32');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_posts`
--

DROP TABLE IF EXISTS `user_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_posts` (
                              `user_id` int NOT NULL,
                              `posts_id` int NOT NULL,
                              PRIMARY KEY (`user_id`,`posts_id`),
                              UNIQUE KEY `UKhderxn09m1aorawdorx55782j` (`posts_id`),
                              CONSTRAINT `FK52ebjx0bagapf4x475m3xbofc` FOREIGN KEY (`posts_id`) REFERENCES `post` (`id`),
                              CONSTRAINT `FKte524koqa2jvulb0h5o99x7f1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_posts`
--

LOCK TABLES `user_posts` WRITE;
/*!40000 ALTER TABLE `user_posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- User structure for Campnews User
--

CREATE USER 'campnews'@'%' IDENTIFIED BY 'Camp4News';
GRANT ALL PRIVILEGES ON campnews.* TO 'campnews'@'%';

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-04 10:34:26
