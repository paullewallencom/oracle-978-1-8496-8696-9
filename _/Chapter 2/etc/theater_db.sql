-- phpMyAdmin SQL Dump
-- version 3.5.8.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 20, 2013 at 02:20 PM
-- Server version: 5.5.31-0ubuntu0.13.04.1
-- PHP Version: 5.4.9-4ubuntu2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `theater_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `exhibition`
--

CREATE TABLE IF NOT EXISTS `exhibition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `hour` int(4) NOT NULL,
  `movieRef` int(11) NOT NULL,
  `roomRef` int(11) NOT NULL,
  `available_seats` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `roomRef` (`roomRef`),
  KEY `movieRef` (`movieRef`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

--
-- Dumping data for table `exhibition`
--

INSERT INTO `exhibition` (`id`, `date`, `hour`, `movieRef`, `roomRef`, `available_seats`) VALUES
(3, '2013-06-01', 1200, 5, 1, 100),
(4, '2013-06-01', 1500, 5, 1, 100),
(5, '2013-06-03', 1200, 6, 2, 50),
(6, '2013-06-03', 1500, 6, 2, 50),
(7, '2013-06-05', 1900, 7, 1, 100),
(8, '2013-06-05', 2000, 7, 2, 50),
(9, '2013-06-07', 1830, 8, 1, 100),
(10, '2013-06-07', 2130, 8, 2, 50),
(11, '2013-06-02', 1300, 5, 1, 100),
(12, '2013-06-02', 1600, 5, 1, 100),
(13, '2013-06-04', 1200, 6, 2, 50),
(14, '2013-06-04', 1500, 6, 2, 50),
(15, '2013-06-06', 2000, 7, 2, 50),
(16, '2013-06-08', 1730, 8, 1, 100),
(17, '2013-06-08', 2230, 8, 2, 50);

-- --------------------------------------------------------

--
-- Table structure for table `movie`
--

CREATE TABLE IF NOT EXISTS `movie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `description` varchar(4096) DEFAULT NULL,
  `length` int(4) DEFAULT NULL COMMENT 'In minutes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `movie`
--

INSERT INTO `movie` (`id`, `name`, `description`, `length`) VALUES
(5, 'Silent Kill', 'Some description', 108),
(6, 'Finding Memo', 'Some description', 84),
(7, 'Chasing Dodges', 'Some description', 112),
(8, 'Frankenwurst', 'Some description', 98);

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE IF NOT EXISTS `room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `capacity` int(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`id`, `name`, `capacity`) VALUES
(1, 'Room B', 100),
(2, 'Room A', 50);

-- --------------------------------------------------------

--
-- Table structure for table `seat`
--

CREATE TABLE IF NOT EXISTS `seat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(2) NOT NULL,
  `price` decimal(10,0) NOT NULL,
  `roomRef` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `roomRef` (`roomRef`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `seat`
--

INSERT INTO `seat` (`id`, `type`, `price`, `roomRef`) VALUES
(1, 1, 8, 1),
(2, 2, 12, 1),
(3, 1, 7, 2),
(4, 2, 10, 2),
(5, 3, 7, 2);

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

--
-- Constraints for dumped tables
--

--
-- Constraints for table `exhibition`
--
ALTER TABLE `exhibition`
  ADD CONSTRAINT `exhibition_ibfk_1` FOREIGN KEY (`movieRef`) REFERENCES `movie` (`id`),
  ADD CONSTRAINT `exhibition_ibfk_2` FOREIGN KEY (`roomRef`) REFERENCES `room` (`id`);

--
-- Constraints for table `seat`
--
ALTER TABLE `seat`
  ADD CONSTRAINT `seat_ibfk_1` FOREIGN KEY (`roomRef`) REFERENCES `room` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
