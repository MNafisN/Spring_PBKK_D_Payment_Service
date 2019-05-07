/*
SQLyog Ultimate v12.09 (32 bit)
MySQL - 5.6.20 : Database - payment_service
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`payment_service` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `payment_service`;

/*Table structure for table `food_order` */

DROP TABLE IF EXISTS `food_order`;

CREATE TABLE `food_order` (
  `id_food_order` char(5) NOT NULL,
  `id_wallet` char(5) DEFAULT NULL,
  `id_transactions` char(5) DEFAULT NULL,
  `food_order_bill` decimal(19,4) DEFAULT NULL,
  `food_order_wallets` decimal(19,4) DEFAULT NULL,
  PRIMARY KEY (`id_food_order`),
  KEY `FK_WalletOrder` (`id_wallet`),
  KEY `FK_TransactionsOrder` (`id_transactions`),
  CONSTRAINT `FK_TransactionsOrder` FOREIGN KEY (`id_transactions`) REFERENCES `transactions` (`id_transactions`),
  CONSTRAINT `FK_WalletOrder` FOREIGN KEY (`id_wallet`) REFERENCES `wallet` (`id_wallet`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `food_order` */

/*Table structure for table `top_up` */

DROP TABLE IF EXISTS `top_up`;

CREATE TABLE `top_up` (
  `id_top_up` char(5) NOT NULL,
  `id_wallet` char(5) DEFAULT NULL,
  `id_transactions` char(5) DEFAULT NULL,
  `top_up_balance` decimal(19,4) DEFAULT NULL,
  PRIMARY KEY (`id_top_up`),
  KEY `FK_WalletTopUp` (`id_wallet`),
  KEY `FK_TransactionsTopUp` (`id_transactions`),
  CONSTRAINT `FK_TransactionsTopUp` FOREIGN KEY (`id_transactions`) REFERENCES `transactions` (`id_transactions`),
  CONSTRAINT `FK_WalletTopUp` FOREIGN KEY (`id_wallet`) REFERENCES `wallet` (`id_wallet`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `top_up` */

/*Table structure for table `transactions` */

DROP TABLE IF EXISTS `transactions`;

CREATE TABLE `transactions` (
  `id_transactions` char(5) NOT NULL,
  `transactions_createdDate` date DEFAULT NULL,
  `transactions_lastModifiedDate` date DEFAULT NULL,
  `transactions_type` varchar(2) DEFAULT NULL,
  `transactions_value` decimal(19,4) DEFAULT NULL,
  PRIMARY KEY (`id_transactions`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `transactions` */

/*Table structure for table `wallet` */

DROP TABLE IF EXISTS `wallet`;

CREATE TABLE `wallet` (
  `id_wallet` char(5) NOT NULL,
  `wallet_createdDate` date DEFAULT NULL,
  `wallet_lastModifiedDate` date DEFAULT NULL,
  `wallet_number` varchar(14) DEFAULT NULL,
  `wallet_type` varchar(2) DEFAULT NULL,
  `wallet_balance` decimal(19,4) DEFAULT NULL,
  PRIMARY KEY (`id_wallet`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `wallet` */

/*Table structure for table `withdrawal` */

DROP TABLE IF EXISTS `withdrawal`;

CREATE TABLE `withdrawal` (
  `id_withdrawal` char(5) NOT NULL,
  `id_wallet` char(5) DEFAULT NULL,
  `id_transactions` char(5) DEFAULT NULL,
  `withdrawal_amount` decimal(19,4) DEFAULT NULL,
  PRIMARY KEY (`id_withdrawal`),
  KEY `FK_WalletWithdrawal` (`id_wallet`),
  KEY `FK_TransactionsWithdrawal` (`id_transactions`),
  CONSTRAINT `FK_TransactionsWithdrawal` FOREIGN KEY (`id_transactions`) REFERENCES `transactions` (`id_transactions`),
  CONSTRAINT `FK_WalletWithdrawal` FOREIGN KEY (`id_wallet`) REFERENCES `wallet` (`id_wallet`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `withdrawal` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
