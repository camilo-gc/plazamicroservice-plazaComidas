CREATE DATABASE  IF NOT EXISTS `plaza-powerup` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `plaza-powerup`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: plaza-powerup
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'plato suave para iniciar','Entrada'),(2,'plato especialmente para almuerzos','Fuerte'),(3,'alimento dulce','postre');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `dishes`
--

LOCK TABLES `dishes` WRITE;
/*!40000 ALTER TABLE `dishes` DISABLE KEYS */;
INSERT INTO `dishes` VALUES (2,_binary '','actualizado el domingo','pancitos','2222','asdasdasdasd',1,1),(3,_binary '','cuhrros pequeños rellenos de queso y bocadillo','churritos','9550','sabrozon.com/recursos/menu/platos/imagen.jpg',1,6),(4,_binary '','arroz blanco cocido','Arroz','16500','http://sabrozon.com/recursos/menu/dish/imagen.jpg',2,4),(5,_binary '','frijol cocido','frijol','16500','http://milonga.com/recursos/menu/dish/imagen.jpg',2,4),(6,_binary '','helado','healo','10500','http://milonga.com/recursos/menu/dish/imagen.jpg',3,4),(7,_binary '','pastas con queso','pastas','17500','http://milonga.com/recursos/menu/dish/imagen.jpg',2,4);
/*!40000 ALTER TABLE `dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `employees_restaurants`
--

LOCK TABLES `employees_restaurants` WRITE;
/*!40000 ALTER TABLE `employees_restaurants` DISABLE KEYS */;
INSERT INTO `employees_restaurants` VALUES (1,12,2),(2,13,5),(3,3,4),(4,NULL,5),(6,17,5);
/*!40000 ALTER TABLE `employees_restaurants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `order_dishes`
--

LOCK TABLES `order_dishes` WRITE;
/*!40000 ALTER TABLE `order_dishes` DISABLE KEYS */;
INSERT INTO `order_dishes` VALUES (10,1,4,9),(11,2,5,9),(12,1,7,10),(13,2,6,10),(14,3,5,10),(15,2,4,10),(16,1,7,11),(17,2,6,11),(18,3,5,11),(19,2,4,11),(20,1,7,12),(21,2,6,12),(22,3,5,12),(23,2,4,12);
/*!40000 ALTER TABLE `order_dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (9,'2023-06-04 20:47:41.884000',4,'Preparation',3,4),(10,'2023-06-06 16:48:11.115000',8,'Pending',NULL,4),(11,'2023-06-06 16:50:08.682000',9,'Preparation',3,4),(12,'2023-06-06 16:50:08.682000',10,'Preparation',3,4);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `restaurants`
--

LOCK TABLES `restaurants` WRITE;
/*!40000 ALTER TABLE `restaurants` DISABLE KEYS */;
INSERT INTO `restaurants` VALUES (1,'string',2,'pepe food','111','+793247501667','http://pepefood.com/recursos/logo.jpg'),(2,'string',2,'para & coma','222','+793247501667','http://pare&coma.com/recursos/logo.jpg'),(3,'string',2,'corrientazo','333','+793247501667','http://corrientazo.com/recursos/logo.jpg'),(4,'string',2,'milonga','444','+793247501667','http://milonga.com/recursos/logo.jpg'),(5,'string',5,'candela & sabor','2121','+518875162212','string'),(6,'string',5,'Sabrozon','3131','+518875162212','sabrozon.com/recursos/logo.jpg'),(7,'string',5,'doña pepa','6565','+518875162212','string');
/*!40000 ALTER TABLE `restaurants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'plaza-powerup'
--

--
-- Dumping routines for database 'plaza-powerup'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-21 17:59:52
