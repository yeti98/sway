-- MySQL dump 10.13  Distrib 5.7.28, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: swaydb
-- ------------------------------------------------------
-- Server version	5.7.28-0ubuntu0.19.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `hidden` tinyint(4) NOT NULL,
  `m_index` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_posts`
--

DROP TABLE IF EXISTS `menu_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu_posts` (
  `menu_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_nraekrbwokp95y2vspbdtp24n` (`post_id`),
  KEY `FK6len0oeacah8s56ix4p441pri` (`menu_id`),
  CONSTRAINT `FK6len0oeacah8s56ix4p441pri` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `FKk3xfspklwglaipf2gc3i254cf` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_posts`
--

LOCK TABLES `menu_posts` WRITE;
/*!40000 ALTER TABLE `menu_posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `menu_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contents` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkgkwirbq1g4eajnbd9igx9ccf` (`author_id`),
  CONSTRAINT `FKkgkwirbq1g4eajnbd9igx9ccf` FOREIGN KEY (`author_id`) REFERENCES `susers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `answer` text NOT NULL,
  `choices` text NOT NULL,
  `content` text NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `explanation` text,
  `question_id` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` (`id`, `active`, `answer`, `choices`, `content`, `created_at`, `explanation`, `question_id`, `updated_at`) VALUES (1,_binary '','posted','posted','Quá khứ phân từ 2 của post?','2020-08-09 18:15:46.792000','','CH01','2020-08-09 18:15:46.792000'),(2,_binary '','A','A###DEVCULI###B###DEVCULI###C###DEVCULI###D','Nội dung mới','2020-08-09 18:23:12.615000','','CH2','2020-08-09 18:23:12.615000'),(3,_binary '','X','A###DEVCULI###X###DEVCULI###z###DEVCULI###Y','Noi dung 1','2020-08-09 18:26:53.130000','','CH','2020-08-09 18:26:53.130000'),(4,_binary '','X','A###DEVCULI###X###DEVCULI###z###DEVCULI###Y','Noi dung 1','2020-08-09 18:27:22.051000','','CH','2020-08-09 18:27:22.051000'),(5,_binary '','X','A###DEVCULI###X###DEVCULI###z###DEVCULI###Y','Noi dung 1','2020-08-09 18:27:25.384000','','CH','2020-08-09 18:27:25.384000'),(6,_binary '','X','A###DEVCULI###X###DEVCULI###z###DEVCULI###Y','Noi dung 1','2020-08-09 18:27:28.240000','','CH','2020-08-09 18:27:28.240000'),(7,_binary '','X','A###DEVCULI###X###DEVCULI###z###DEVCULI###Y','Noi dung 1','2020-08-09 18:27:30.802000','','CH','2020-08-09 18:27:30.802000'),(8,_binary '','X','A###DEVCULI###X###DEVCULI###z###DEVCULI###Y','Noi dung 1','2020-08-09 18:27:32.749000','','CH','2020-08-09 18:27:32.749000'),(9,_binary '','X','A###DEVCULI###X###DEVCULI###z###DEVCULI###Y','Noi dung 1','2020-08-09 18:27:34.956000','','CH','2020-08-09 18:27:34.956000'),(10,_binary '','X','A###DEVCULI###X###DEVCULI###z###DEVCULI###Y','Noi dung 1','2020-08-09 18:27:36.871000','','CH','2020-08-09 18:27:36.871000'),(11,_binary '','X','A###DEVCULI###X###DEVCULI###z###DEVCULI###Y','Noi dung 1','2020-08-09 18:27:39.846000','','CH','2020-08-09 18:27:39.846000'),(12,_binary '','X','A###DEVCULI###X###DEVCULI###z###DEVCULI###Y','Noi dung 1','2020-08-09 18:27:43.825000','','CH','2020-08-09 18:27:43.825000');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sclass_lecturers`
--

DROP TABLE IF EXISTS `sclass_lecturers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sclass_lecturers` (
  `suser_id` bigint(20) NOT NULL,
  `sclass_id` bigint(20) NOT NULL,
  PRIMARY KEY (`suser_id`,`sclass_id`),
  KEY `FKb0l7u0d1oii88j76ykb1pi3fu` (`sclass_id`),
  CONSTRAINT `FK262v3ka48phub20cnlkcxdqn3` FOREIGN KEY (`suser_id`) REFERENCES `sclasses` (`id`),
  CONSTRAINT `FKb0l7u0d1oii88j76ykb1pi3fu` FOREIGN KEY (`sclass_id`) REFERENCES `susers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sclass_lecturers`
--

LOCK TABLES `sclass_lecturers` WRITE;
/*!40000 ALTER TABLE `sclass_lecturers` DISABLE KEYS */;
/*!40000 ALTER TABLE `sclass_lecturers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sclass_students`
--

DROP TABLE IF EXISTS `sclass_students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sclass_students` (
  `suser_id` bigint(20) NOT NULL,
  `sclass_id` bigint(20) NOT NULL,
  KEY `FKjbnwk35q108nfr6cq4e7a1fdb` (`sclass_id`),
  KEY `FK7ot2xkt7yw9d00jui38aj749h` (`suser_id`),
  CONSTRAINT `FK7ot2xkt7yw9d00jui38aj749h` FOREIGN KEY (`suser_id`) REFERENCES `sclasses` (`id`),
  CONSTRAINT `FKjbnwk35q108nfr6cq4e7a1fdb` FOREIGN KEY (`sclass_id`) REFERENCES `susers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sclass_students`
--

LOCK TABLES `sclass_students` WRITE;
/*!40000 ALTER TABLE `sclass_students` DISABLE KEYS */;
/*!40000 ALTER TABLE `sclass_students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sclasses`
--

DROP TABLE IF EXISTS `sclasses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sclasses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `course_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdnkfqi8wum16xuanramgit0ag` (`course_id`),
  CONSTRAINT `FKdnkfqi8wum16xuanramgit0ag` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sclasses`
--

LOCK TABLES `sclasses` WRITE;
/*!40000 ALTER TABLE `sclasses` DISABLE KEYS */;
/*!40000 ALTER TABLE `sclasses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slessons`
--

DROP TABLE IF EXISTS `slessons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `slessons` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `course_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5pvg6blq4p9nsjv7p6eb49nyt` (`course_id`),
  CONSTRAINT `FK5pvg6blq4p9nsjv7p6eb49nyt` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slessons`
--

LOCK TABLES `slessons` WRITE;
/*!40000 ALTER TABLE `slessons` DISABLE KEYS */;
/*!40000 ALTER TABLE `slessons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slessons_tests`
--

DROP TABLE IF EXISTS `slessons_tests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `slessons_tests` (
  `lesson_id` bigint(20) NOT NULL,
  `tests_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_bg74fxn6j5nsngo9pisjdy14b` (`tests_id`),
  KEY `FKnoi08q7h0anc96t90xurs8ukf` (`lesson_id`),
  CONSTRAINT `FK9qivx4shh27x9tjl1dbkrfwbl` FOREIGN KEY (`tests_id`) REFERENCES `stests` (`id`),
  CONSTRAINT `FKnoi08q7h0anc96t90xurs8ukf` FOREIGN KEY (`lesson_id`) REFERENCES `slessons` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slessons_tests`
--

LOCK TABLES `slessons_tests` WRITE;
/*!40000 ALTER TABLE `slessons_tests` DISABLE KEYS */;
/*!40000 ALTER TABLE `slessons_tests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ssubmits`
--

DROP TABLE IF EXISTS `ssubmits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ssubmits` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `is_checked` varchar(255) DEFAULT NULL,
  `lecturer_note` varchar(255) DEFAULT NULL,
  `score` double DEFAULT NULL,
  `student_contact` varchar(255) DEFAULT NULL,
  `student_name` varchar(255) DEFAULT NULL,
  `student_note` varchar(255) DEFAULT NULL,
  `submit_type` int(11) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `check_user_id` bigint(20) DEFAULT NULL,
  `sway_test_id` bigint(20) DEFAULT NULL,
  `sway_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrsfibic6p88gm8gje1ab9ta8l` (`check_user_id`),
  KEY `FKag92d61r8knqkh7pqdoirea86` (`sway_test_id`),
  KEY `FK5yce43e299eo70ereni76bn2y` (`sway_user_id`),
  CONSTRAINT `FK5yce43e299eo70ereni76bn2y` FOREIGN KEY (`sway_user_id`) REFERENCES `susers` (`id`),
  CONSTRAINT `FKag92d61r8knqkh7pqdoirea86` FOREIGN KEY (`sway_test_id`) REFERENCES `stests` (`id`),
  CONSTRAINT `FKrsfibic6p88gm8gje1ab9ta8l` FOREIGN KEY (`check_user_id`) REFERENCES `susers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ssubmits`
--

LOCK TABLES `ssubmits` WRITE;
/*!40000 ALTER TABLE `ssubmits` DISABLE KEYS */;
/*!40000 ALTER TABLE `ssubmits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stest_submits`
--

DROP TABLE IF EXISTS `stest_submits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stest_submits` (
  `sway_test_id` bigint(20) NOT NULL,
  `submits_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_sclsecjvub0pt5ys2a4f4ioy7` (`submits_id`),
  KEY `FKjq4t0d427yrlltyrqoaputcs4` (`sway_test_id`),
  CONSTRAINT `FKjfxih0eusglclecmbewuju1wk` FOREIGN KEY (`submits_id`) REFERENCES `ssubmits` (`id`),
  CONSTRAINT `FKjq4t0d427yrlltyrqoaputcs4` FOREIGN KEY (`sway_test_id`) REFERENCES `stests` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stest_submits`
--

LOCK TABLES `stest_submits` WRITE;
/*!40000 ALTER TABLE `stest_submits` DISABLE KEYS */;
/*!40000 ALTER TABLE `stest_submits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stests`
--

DROP TABLE IF EXISTS `stests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stests` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `deadline` date DEFAULT NULL,
  `test_id` varchar(255) DEFAULT NULL,
  `test_name` varchar(255) DEFAULT NULL,
  `test_type` int(11) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stests`
--

LOCK TABLES `stests` WRITE;
/*!40000 ALTER TABLE `stests` DISABLE KEYS */;
INSERT INTO `stests` (`id`, `active`, `created_at`, `deadline`, `test_id`, `test_name`, `test_type`, `updated_at`) VALUES (1,_binary '','2020-08-09 18:14:31.053000',NULL,'HW001','Bài tập về nhà số 01',1,'2020-08-09 18:16:10.164000');
/*!40000 ALTER TABLE `stests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stests_questions`
--

DROP TABLE IF EXISTS `stests_questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stests_questions` (
  `sway_test_id` bigint(20) NOT NULL,
  `questions_id` bigint(20) NOT NULL,
  KEY `FKdtoi6sqck2i74ilbapwk8yw9j` (`questions_id`),
  KEY `FKhb85b0agbwdmwsve5nfnrwm8e` (`sway_test_id`),
  CONSTRAINT `FKdtoi6sqck2i74ilbapwk8yw9j` FOREIGN KEY (`questions_id`) REFERENCES `questions` (`id`),
  CONSTRAINT `FKhb85b0agbwdmwsve5nfnrwm8e` FOREIGN KEY (`sway_test_id`) REFERENCES `stests` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stests_questions`
--

LOCK TABLES `stests_questions` WRITE;
/*!40000 ALTER TABLE `stests_questions` DISABLE KEYS */;
INSERT INTO `stests_questions` (`sway_test_id`, `questions_id`) VALUES (1,1),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11);
/*!40000 ALTER TABLE `stests_questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `susers`
--

DROP TABLE IF EXISTS `susers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `susers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `salt_value` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `username` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `susers`
--

LOCK TABLES `susers` WRITE;
/*!40000 ALTER TABLE `susers` DISABLE KEYS */;
INSERT INTO `susers` (`id`, `avatar`, `created_at`, `description`, `name`, `password`, `salt_value`, `status`, `type`, `updated_at`, `username`, `role`) VALUES (1,NULL,'2020-08-05 00:01:29.000000','','Nguyen Huu Trang','GWIfVRbutijXObNLuOdEq5JdNCJhVfawI1AqtK5YafAZ/tlG/2F0a2IPHAn+0b3GUTg4cvZwkm35MuUrr+27SZsegTxN05Y05RYeGns4ezuRWj0+1JOhu6cWzEmj9b4ZOVrz/1tLuWRPlj3ynnoF7g==','5UQlnWlEeLk=','0',NULL,'2020-08-05 00:02:17.000000','trangnh@gmail.com','ADMIN');
/*!40000 ALTER TABLE `susers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `susers_sclasses`
--

DROP TABLE IF EXISTS `susers_sclasses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `susers_sclasses` (
  `suser_id` bigint(20) NOT NULL,
  `sclass_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_g7ulgcsfqcmkvkyk7oj5g0lqi` (`sclass_id`),
  KEY `FKdr652rohbevoudvd5j2ol7qpr` (`suser_id`),
  CONSTRAINT `FKdr652rohbevoudvd5j2ol7qpr` FOREIGN KEY (`suser_id`) REFERENCES `susers` (`id`),
  CONSTRAINT `FKt3w6cxbmvfa0y2ir7u076860e` FOREIGN KEY (`sclass_id`) REFERENCES `sclasses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `susers_sclasses`
--

LOCK TABLES `susers_sclasses` WRITE;
/*!40000 ALTER TABLE `susers_sclasses` DISABLE KEYS */;
/*!40000 ALTER TABLE `susers_sclasses` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-10  0:33:12
