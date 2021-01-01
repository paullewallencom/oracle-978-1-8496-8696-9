-- phpMyAdmin SQL Dump
-- version 3.5.8.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 23, 2013 at 06:42 AM
-- Server version: 5.5.31-0ubuntu0.13.04.1
-- PHP Version: 5.4.9-4ubuntu2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `store_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `movie`
--

CREATE TABLE IF NOT EXISTS `movie` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(4096) DEFAULT NULL,
  `length` int(4) DEFAULT NULL COMMENT 'In minutes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `movie`
--

INSERT INTO `movie` (`id`, `name`, `description`, `length`) VALUES
(5, 'Silent Kill', 'some description', 108),
(6, 'Finding Memo', 'some description', 84),
(7, 'Chasing Dodges', 'some description', 112),
(8, 'Frankenwurst', 'some description', 98);

-- --------------------------------------------------------

--
-- Table structure for table `sequence`
--

CREATE TABLE IF NOT EXISTS `sequence` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT '1',
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sequence`
--

INSERT INTO `sequence` (`SEQ_NAME`, `SEQ_COUNT`) VALUES
('SEQ_GEN', 1),
('SEQ_GEN_TABLE', 10000);

-- --------------------------------------------------------

--
-- Table structure for table `theater`
--

CREATE TABLE IF NOT EXISTS `theater` (
  `id` int(11) NOT NULL,
  `name` varchar(80) NOT NULL,
  `address` varchar(200) NOT NULL,
  `city` varchar(75) NOT NULL,
  `phoneNumber` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `theater`
--

INSERT INTO `theater` (`id`, `name`, `address`, `city`, `phoneNumber`) VALUES
(2, 'AMD Cupertino Cube', '100 Eastridge Loop', 'Cupertino', '183190389'),
(3, 'AMD Van Ness', '1001 Van Ness Avenue', 'San Francisco', '91824819'),
(4, 'AMD Bay Street', '9999 Bay Street', 'Emeryville', '129874012');

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE IF NOT EXISTS `ticket` (
  `id` int(11) NOT NULL,
  `theaterRef` int(11) NOT NULL,
  `customerRef` int(11) DEFAULT NULL,
  `exhibitionRef` int(11) NOT NULL,
  `control` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `wl_llr_adminserver`
--

CREATE TABLE IF NOT EXISTS `wl_llr_adminserver` (
  `XIDSTR` varchar(40) NOT NULL,
  `POOLNAMESTR` varchar(64) DEFAULT NULL,
  `RECORDSTR` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`XIDSTR`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `wl_llr_adminserver`
--

INSERT INTO `wl_llr_adminserver` (`XIDSTR`, `POOLNAMESTR`, `RECORDSTR`) VALUES
('JDBC LLR Domain//Server', 'JDBC LLR Domain//Server', 'tickets//AdminServer'),
('JDBC LLR Version', 'JDBC LLR Version', '1.0');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
