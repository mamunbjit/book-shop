-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 12, 2023 at 11:43 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bookshopdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` bigint(20) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `availability` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `rating` double NOT NULL,
  `title` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `author`, `availability`, `description`, `rating`, `title`) VALUES
(1, 'John Smith', b'1', 'A gripping novel about adventure', 3.1666666666666665, 'The Adventure Begins'),
(2, 'Jane Doe', b'1', 'A heartwarming story of love and loss', 4.25, 'Love and Loss Chronicles'),
(3, 'Mark Johnson Jr.', b'1', 'An epic fantasy tale of heroes and magic', 5, 'The Quest for Magic'),
(4, 'Sarah Adams', b'1', 'A guide to SQL for beginners', 4.9, 'SQL Fundamentals 101');

-- --------------------------------------------------------

--
-- Table structure for table `borrow`
--

CREATE TABLE `borrow` (
  `borrowed_id` bigint(20) NOT NULL,
  `borrow_date` datetime(6) DEFAULT NULL,
  `return_date` datetime(6) DEFAULT NULL,
  `book_id` bigint(20) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `borrow`
--

INSERT INTO `borrow` (`borrowed_id`, `borrow_date`, `return_date`, `book_id`, `user_id`) VALUES
(6, '2023-08-28 12:36:56.000000', '2023-09-07 12:43:18.000000', 1, 2),
(7, '2023-08-28 13:53:10.000000', '2023-09-07 13:58:10.000000', 2, 3),
(8, '2023-08-28 14:00:23.000000', '2023-09-07 14:02:25.000000', 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `reservation_id` bigint(20) NOT NULL,
  `notification_status` bit(1) DEFAULT NULL,
  `reservation_date` datetime(6) DEFAULT NULL,
  `book_id` bigint(20) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`reservation_id`, `notification_status`, `reservation_date`, `book_id`, `user_id`) VALUES
(1, b'1', '2023-09-12 00:00:00.000000', 1, 2),
(2, b'1', '2023-09-13 00:00:00.000000', 2, 3),
(3, b'1', '2023-09-14 00:00:00.000000', 3, 4);

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `review_id` bigint(20) NOT NULL,
  `rating` double NOT NULL,
  `review_text` varchar(255) DEFAULT NULL,
  `book_id` bigint(20) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`review_id`, `rating`, `review_text`, `book_id`, `user_id`) VALUES
(1, 5, 'This book was amazing! I could not put it down.', 1, 1),
(2, 4, 'A touching story, but it had a slow start.', 2, 2),
(3, 5, 'Epic fantasy at its best! Loved the characters.', 3, 3),
(4, 4, 'Great introduction to SQL for beginners.', 4, 4),
(6, 4.5, 'This book was really enjoyable. I would recommend it.', 1, 3),
(7, 4.5, 'This book was really enjoyable. I would recommend it.', 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `address`, `email`, `first_name`, `last_name`, `password`, `role`) VALUES
(1, '123 Main Street, Apt 4B, City, Country', 'user1@email.com', 'John', 'Doe', '123', NULL),
(2, '456 Elm Street, Suite 2A, Another City, Another Country', 'user2@email.com', 'Jane', 'Smith', '123', NULL),
(3, '789 Oak Avenue, Unit 7, Yet Another City, Yet Another Country', 'user3@email.com', 'Michael', 'Johnson', '123', NULL),
(4, '101 Pine Road, House 12, Different City, Different Country', 'user4@email.com', 'Emily', 'Davis', '123', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `borrow`
--
ALTER TABLE `borrow`
  ADD PRIMARY KEY (`borrowed_id`),
  ADD KEY `FKtrw1lxelfcyso4uenvk1wy9q5` (`book_id`),
  ADD KEY `FKrby7un94muby8qxwumbkioiu9` (`user_id`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`reservation_id`),
  ADD KEY `FKs25sh1gv4uidcd1c1qjux3af2` (`book_id`),
  ADD KEY `FKrea93581tgkq61mdl13hehami` (`user_id`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`review_id`),
  ADD KEY `FK880c0rw9ffetwe2p1qb7x4icf` (`book_id`),
  ADD KEY `FK6cpw2nlklblpvc7hyt7ko6v3e` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `borrow`
--
ALTER TABLE `borrow`
  MODIFY `borrowed_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `reservation_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `review_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `borrow`
--
ALTER TABLE `borrow`
  ADD CONSTRAINT `FKrby7un94muby8qxwumbkioiu9` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `FKtrw1lxelfcyso4uenvk1wy9q5` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`);

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `FKrea93581tgkq61mdl13hehami` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `FKs25sh1gv4uidcd1c1qjux3af2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`);

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `FK6cpw2nlklblpvc7hyt7ko6v3e` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `FK880c0rw9ffetwe2p1qb7x4icf` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
