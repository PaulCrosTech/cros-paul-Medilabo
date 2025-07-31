CREATE TABLE IF NOT EXISTS `patient` (
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `patient_id` int NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(12) DEFAULT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `birth_date` varchar(255) NOT NULL,
  `gender` enum('M','F') NOT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `patient` (`deleted`, `patient_id`, `phone_number`, `first_name`, `last_name`, `address`, `birth_date`, `gender`) VALUES
	(0, 1, '100-222-3333', 'Test', 'TestNone', '1 Brookside St', '1966-12-03', 'F'),
	(0, 2, '200-333-4444', 'Test', 'TestBorderline', '2 High St ', '1945-06-24', 'M'),
	(0, 3, '300-444-5555', 'Test', 'TestInDanger', '3 Club Road', '2004-06-18', 'M'),
	(0, 4, '400-555-6666', 'Test', 'TestEarlyOnset', '4 Valley Dr ', '2002-06-28', 'F');
