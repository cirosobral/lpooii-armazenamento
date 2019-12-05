DROP DATABASE IF EXISTS `javapersistence`;
CREATE DATABASE IF NOT EXISTS `javapersistence`;
GRANT ALL PRIVILEGES ON `javapersistence`.* TO 
'dbadmin'@'localhost' IDENTIFIED BY 'senhasupersecreta';
USE `javapersistence`;

CREATE TABLE `cliente` (
  `id` int(11) AUTO_INCREMENT PRIMARY KEY,
  `nome` varchar(255) NOT NULL,
  `sobrenome` varchar(255) NOT NULL
) ENGINE=InnoDB;