--
-- Скрипт сгенерирован Devart dbForge Studio for MySQL, Версия 6.3.358.0
-- Домашняя страница продукта: http://www.devart.com/ru/dbforge/mysql/studio
-- Дата скрипта: 30.07.2015 17:29:08
-- Версия сервера: 5.7.7-rc-log
-- Версия клиента: 4.1
--


USE java_blog;

CREATE TABLE blg_dic_city (
  city_id int(11) NOT NULL,
  city_name varchar(50) DEFAULT NULL,
  country_id int(11) DEFAULT NULL,
  PRIMARY KEY (city_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_dic_country (
  country_id int(11) NOT NULL,
  country_name varchar(50) NOT NULL,
  PRIMARY KEY (country_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_dic_role (
  role_id int(11) NOT NULL AUTO_INCREMENT,
  role_name varchar(20) NOT NULL,
  PRIMARY KEY (role_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 3
AVG_ROW_LENGTH = 8192
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_dic_telephone_type (
  tel_type_id int(11) NOT NULL AUTO_INCREMENT,
  tel_type_name varchar(50) DEFAULT NULL,
  PRIMARY KEY (tel_type_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 3
AVG_ROW_LENGTH = 8192
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_user (
  usr_id int(11) NOT NULL AUTO_INCREMENT,
  usr_dateTimeChange timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  usr_login varchar(30) NOT NULL,
  usr_password varchar(100) NOT NULL,
  PRIMARY KEY (usr_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 33
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_user_mail (
  usr_mail_id int(11) NOT NULL,
  usr_id int(11) NOT NULL,
  usr_mail varchar(50) NOT NULL,
  usr_mail_domain varchar(20) NOT NULL,
  PRIMARY KEY (usr_mail_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_user_telephone (
  usr_tel_id int(11) NOT NULL AUTO_INCREMENT,
  usr_tel_numb varchar(20) NOT NULL,
  usr_id int(11) NOT NULL,
  tel_type_id int(11) NOT NULL,
  PRIMARY KEY (usr_tel_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 6
AVG_ROW_LENGTH = 4096
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

CREATE TABLE blg_user_detail (
  usr_det_id int(11) NOT NULL AUTO_INCREMENT,
  usr_det_adres varchar(100) DEFAULT NULL,
  usr_det_birthdate date DEFAULT NULL,
  usr_det_city int(11) DEFAULT NULL,
  usr_det_country int(11) DEFAULT NULL,
  usr_det_firstname varchar(20) NOT NULL,
  usr_det_lastname varchar(20) NOT NULL,
  usr_id int(11) NOT NULL,
  PRIMARY KEY (usr_det_id),
  UNIQUE INDEX unique_usr_det_id (usr_det_id),
  UNIQUE INDEX usr_id (usr_id),
  CONSTRAINT FK_blg_user_detail_blg_user_usr_id FOREIGN KEY (usr_id)
  REFERENCES blg_user (usr_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 32
AVG_ROW_LENGTH = 4096
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_user_role (
  usr_id int(11) NOT NULL,
  role_id int(11) NOT NULL,
  CONSTRAINT FK_blg_user_role_blg_dic_role_role_id FOREIGN KEY (role_id)
  REFERENCES blg_dic_role (role_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT FK_blg_user_role_blg_user_usr_id FOREIGN KEY (usr_id)
  REFERENCES blg_user (usr_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci;