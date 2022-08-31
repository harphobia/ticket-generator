-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 19, 2019 at 04:23 AM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.1.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `uas`
--

-- --------------------------------------------------------

--
-- Table structure for table `tiket`
--

CREATE TABLE `tiket` (
  `id` int(4) NOT NULL,
  `nama` varchar(12) NOT NULL,
  `nik` varchar(16) NOT NULL,
  `no_telp` varchar(12) NOT NULL,
  `tanggal` varchar(14) NOT NULL,
  `jenis_tiket` varchar(12) NOT NULL,
  `kelas_tiket` varchar(12) NOT NULL,
  `asal` varchar(12) NOT NULL,
  `tujuan` varchar(12) NOT NULL,
  `kursi` varchar(5) NOT NULL,
  `create_date` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tiket`
--

INSERT INTO `tiket` (`id`, `nama`, `nik`, `no_telp`, `tanggal`, `jenis_tiket`, `kelas_tiket`, `asal`, `tujuan`, `kursi`, `create_date`) VALUES
(1, 'Anhar', '181080200304', '08123456789', 'Dec 31, 2019', 'Pesawat', 'Bisnis', 'Surabaya', 'Bali', 'A4', '2019-12-11'),
(2, 'Endi', '181080200212', '08987654321', 'Dec 1, 2019', 'Kereta', 'Bisnis', 'Mojokerto', 'Bandung', 'A5', '2019-12-11'),
(3, 'Dedy', '181080200199', '08124537282', 'Dec 19, 2019', 'Pesawat', 'Bisnis', 'Malang', 'Papua', 'A3', '2019-12-12'),
(6, 'Balon', '133434213123', '0812345678', 'Dec 19, 2019', 'Kapal', 'Ekonomi', 'Madura', 'Batam', 'D5', '2019-12-12'),
(9, 'Lima', '842938423i', '912833278', 'Dec 25, 2019', 'Pesawat', 'Ekonomi', 'Tangerang', 'Semarang', 'A1', '2019-12-18'),
(11, 'Delman', '32987492837', '23874283947', 'Dec 24, 2019', 'Kereta', 'Ekonomi', 'Semarang', 'Jakarta', 'A1', '2019-12-18'),
(12, 'Minggu', '2378467482', '1237918723', 'Dec 7, 2019', 'Kapal', 'Ekonomi', 'Madura', 'Batam', 'D1', '2019-12-18'),
(13, 'Galih', '126388631172', '368163271638', 'Dec 20, 2019', 'Kereta', 'Ekonomi', 'Semarang', 'Jakarta', 'B1', '2019-12-18'),
(14, 'Ren', '213312312', '234289348', 'Sep 20, 2020', 'Pesawat', 'Bisnis', 'Surabaya', 'Pekanbaru', 'B3', '2019-12-19');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tiket`
--
ALTER TABLE `tiket`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tiket`
--
ALTER TABLE `tiket`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
