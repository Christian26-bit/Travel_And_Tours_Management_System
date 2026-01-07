CREATE DATABASE  IF NOT EXISTS `cht_travel_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cht_travel_db`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cht_travel_db
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `accommodation`
--

DROP TABLE IF EXISTS `accommodation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accommodation` (
  `accommodationId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `address` varchar(50) NOT NULL,
  `contact` varchar(50) DEFAULT NULL,
  `amenities` text,
  `numberOfRooms` int NOT NULL,
  `defaultRoomType` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`accommodationId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity` (
  `activityId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` text,
  `defaultLocation` varchar(50) DEFAULT NULL,
  `defaultDuration` int DEFAULT NULL,
  `isIncludedByDefault` bit(1) DEFAULT b'0',
  PRIMARY KEY (`activityId`)
) ENGINE=InnoDB AUTO_INCREMENT=247 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `BookingID` int NOT NULL AUTO_INCREMENT,
  `EmployeeID` int DEFAULT NULL,
  `ClientID` int DEFAULT NULL,
  `PackageID` int DEFAULT NULL,
  `BookingDate` date DEFAULT NULL,
  `Status` varchar(50) DEFAULT NULL,
  `PaxCount` int DEFAULT NULL,
  PRIMARY KEY (`BookingID`),
  KEY `EmployeeID` (`EmployeeID`),
  KEY `ClientID` (`ClientID`),
  KEY `PackageID` (`PackageID`),
  CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`EmployeeID`) REFERENCES `employee` (`employeeId`),
  CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`ClientID`) REFERENCES `client` (`clientId`),
  CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`PackageID`) REFERENCES `package` (`PackageID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `clientId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL,
  `address` varchar(50) NOT NULL,
  `contactNumber` varchar(15) NOT NULL,
  `customerType` enum('REGULAR','CORPORATE','VIP') DEFAULT 'REGULAR',
  `dateRegistered` date NOT NULL,
  PRIMARY KEY (`clientId`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employeeId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` char(40) NOT NULL,
  `contactNumber` varchar(15) DEFAULT NULL,
  `isManager` bit(1) DEFAULT b'0',
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`employeeId`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `package` (
  `PackageID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Description` text,
  `Destination` varchar(100) DEFAULT NULL,
  `Duration` int DEFAULT NULL,
  `MaxPax` int DEFAULT NULL,
  `Inclusions` text,
  `Price` decimal(10,2) NOT NULL,
  `IsActive` tinyint(1) DEFAULT '1',
  `CreatedByEmployeeID` int DEFAULT NULL,
  `ImagePath` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PackageID`),
  KEY `CreatedByEmployeeID` (`CreatedByEmployeeID`),
  CONSTRAINT `package_ibfk_1` FOREIGN KEY (`CreatedByEmployeeID`) REFERENCES `employee` (`employeeId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `packagetrips`
--

DROP TABLE IF EXISTS `packagetrips`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packagetrips` (
  `PackageTripID` int NOT NULL AUTO_INCREMENT,
  `PackageID` int DEFAULT NULL,
  `TripID` int DEFAULT NULL,
  `Sequence` int DEFAULT NULL,
  `DayOfPackage` int DEFAULT NULL,
  PRIMARY KEY (`PackageTripID`),
  KEY `PackageID` (`PackageID`),
  KEY `TripID` (`TripID`),
  CONSTRAINT `packagetrips_ibfk_1` FOREIGN KEY (`PackageID`) REFERENCES `package` (`PackageID`),
  CONSTRAINT `packagetrips_ibfk_2` FOREIGN KEY (`TripID`) REFERENCES `trip` (`TripID`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `paymentId` int NOT NULL AUTO_INCREMENT,
  `bookingId` int NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `paymentDate` date NOT NULL,
  `method` varchar(64) NOT NULL,
  `status` enum('PENDING','PAID','FAILED','REFUNDED') DEFAULT 'PENDING',
  `referenceNumber` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`paymentId`),
  KEY `fk_payment_booking` (`bookingId`),
  CONSTRAINT `fk_payment_booking` FOREIGN KEY (`bookingId`) REFERENCES `booking` (`BookingID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trip`
--

DROP TABLE IF EXISTS `trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trip` (
  `TripID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(40) NOT NULL,
  `Description` text,
  `Location` varchar(100) DEFAULT NULL,
  `StartDate` date NOT NULL,
  `EndDate` date NOT NULL,
  `IsActive` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`TripID`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tripaccomodations`
--

DROP TABLE IF EXISTS `tripaccomodations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tripaccomodations` (
  `TripAccommodationID` int NOT NULL AUTO_INCREMENT,
  `TripID` int DEFAULT NULL,
  `AccommodationID` int DEFAULT NULL,
  `CheckInDate` date DEFAULT NULL,
  `CheckOutDate` date DEFAULT NULL,
  `RoomType` varchar(50) DEFAULT NULL,
  `IsIncluded` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`TripAccommodationID`),
  KEY `TripID` (`TripID`),
  KEY `AccommodationID` (`AccommodationID`),
  CONSTRAINT `tripaccomodations_ibfk_1` FOREIGN KEY (`TripID`) REFERENCES `trip` (`TripID`),
  CONSTRAINT `tripaccomodations_ibfk_2` FOREIGN KEY (`AccommodationID`) REFERENCES `accommodation` (`accommodationId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tripsactivities`
--

DROP TABLE IF EXISTS `tripsactivities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tripsactivities` (
  `TripActivityID` int NOT NULL AUTO_INCREMENT,
  `TripID` int DEFAULT NULL,
  `ActivityID` int DEFAULT NULL,
  `Location` varchar(105) DEFAULT NULL,
  `StartDateTime` datetime DEFAULT NULL,
  `EndDateTime` datetime DEFAULT NULL,
  `GuideName` varchar(50) DEFAULT NULL,
  `EquipmentProvided` varchar(105) DEFAULT NULL,
  `IsIncluded` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`TripActivityID`),
  KEY `TripID` (`TripID`),
  KEY `ActivityID` (`ActivityID`),
  CONSTRAINT `tripsactivities_ibfk_1` FOREIGN KEY (`TripID`) REFERENCES `trip` (`TripID`),
  CONSTRAINT `tripsactivities_ibfk_2` FOREIGN KEY (`ActivityID`) REFERENCES `activity` (`activityId`)
) ENGINE=InnoDB AUTO_INCREMENT=350 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tripvehicles`
--

DROP TABLE IF EXISTS `tripvehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tripvehicles` (
  `TripVehicleID` int NOT NULL AUTO_INCREMENT,
  `TripID` int DEFAULT NULL,
  `VehicleID` int DEFAULT NULL,
  `DepartureLocation` varchar(100) DEFAULT NULL,
  `ArrivalLocation` varchar(100) DEFAULT NULL,
  `DepartureDateTime` datetime DEFAULT NULL,
  `ArrivalDateTime` datetime DEFAULT NULL,
  `SeatNumber` varchar(20) DEFAULT NULL,
  `TicketReference` varchar(50) DEFAULT NULL,
  `IsIncluded` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`TripVehicleID`),
  KEY `TripID` (`TripID`),
  KEY `VehicleID` (`VehicleID`),
  CONSTRAINT `tripvehicles_ibfk_1` FOREIGN KEY (`TripID`) REFERENCES `trip` (`TripID`),
  CONSTRAINT `tripvehicles_ibfk_2` FOREIGN KEY (`VehicleID`) REFERENCES `vehicle` (`VehicleID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicle` (
  `VehicleID` int NOT NULL AUTO_INCREMENT,
  `Type` varchar(100) NOT NULL,
  `Capacity` int DEFAULT NULL,
  `PlateNumber` varchar(10) DEFAULT NULL,
  `ProviderName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`VehicleID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

-- ==================================================
-- INSERT SUPER ADMIN
-- Email:    admin@cht.com
-- Password: password123
-- ==================================================

INSERT INTO `employee` (`name`, `email`, `password`, `contactNumber`, `isManager`, `isActive`) 
VALUES (
    'Super Admin', 
    'admin@cht.com', 
    'cbfdac6008f9cab4083784cbd1874f76618d2a97', -- SHA-1 hash for "password123"
    '09123456789', 
    b'1', -- 1 = Manager/Admin
    b'1'  -- 1 = Active Account
);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;



-- Dump completed on 2026-01-06  0:10:50
