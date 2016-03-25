﻿USE java_blog;

CREATE TABLE blg_dic_category (
  dic_cat_id int(11) NOT NULL AUTO_INCREMENT,
  dic_cat_name varchar(50) NOT NULL,
  dic_cat_enable tinyint(1) NOT NULL DEFAULT 1,
  dic_cat_pstcount int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (dic_cat_id),
  UNIQUE INDEX pst_cat_name (dic_cat_name)
)
ENGINE = INNODB
AUTO_INCREMENT = 4
AVG_ROW_LENGTH = 5461
CHARACTER SET utf8
COLLATE utf8_general_ci;

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

CREATE TABLE blg_dic_tag (
  dic_tag_id int(11) NOT NULL AUTO_INCREMENT,
  dic_tag_name varchar(50) NOT NULL,
  PRIMARY KEY (dic_tag_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 8
AVG_ROW_LENGTH = 2730
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

CREATE TABLE blg_post (
  pst_id int(11) NOT NULL AUTO_INCREMENT,
  pst_title varchar(255) NOT NULL,
  pst_document text NOT NULL,
  pst_title_image varchar(255) DEFAULT NULL,
  pst_time_create timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  pst_time_modify timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  pst_enable tinyint(1) NOT NULL DEFAULT 0,
  pst_count_like int(11) NOT NULL DEFAULT 0,
  pst_count_dislike int(11) NOT NULL DEFAULT 0,
  pst_count_comm int(11) NOT NULL DEFAULT 0,
  pst_url varchar(255) NOT NULL,
  pst_document_short text NOT NULL,
  pst_count_view int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (pst_id),
  INDEX UK_blg_post_pst_document (pst_document (1)),
  UNIQUE INDEX UK_blg_post_pst_id (pst_id),
  UNIQUE INDEX UK_blg_post_pst_title (pst_title)
)
ENGINE = INNODB
AUTO_INCREMENT = 30
AVG_ROW_LENGTH = 11915
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_user (
  usr_id int(11) NOT NULL AUTO_INCREMENT,
  usr_dateTimeChange timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  usr_login varchar(30) NOT NULL,
  usr_password varchar(100) NOT NULL,
  PRIMARY KEY (usr_id),
  UNIQUE INDEX usr_login (usr_login)
)
ENGINE = INNODB
AUTO_INCREMENT = 38
AVG_ROW_LENGTH = 2340
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

CREATE TABLE blg_comment (
  comm_id int(11) NOT NULL AUTO_INCREMENT,
  comm_document blob NOT NULL,
  comm_time_create timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  comm_enable tinyint(1) NOT NULL DEFAULT 1,
  comm_count_like int(11) NOT NULL DEFAULT 0,
  usr_id int(11) NOT NULL,
  PRIMARY KEY (comm_id),
  UNIQUE INDEX UK_blg_comment_pst_comm_document (comm_document (1)),
  UNIQUE INDEX UK_blg_comment_pst_comm_id (comm_id),
  UNIQUE INDEX UK_blg_post_comment_usr_id (usr_id),
  CONSTRAINT FK_blg_comment_blg_user_usr_id FOREIGN KEY (usr_id)
  REFERENCES blg_user (usr_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_post_category (
  pst_id int(11) DEFAULT NULL,
  dic_cat_id int(11) DEFAULT NULL,
  CONSTRAINT FK_blg_post_category_blg_dic_category_dic_cat_id FOREIGN KEY (dic_cat_id)
  REFERENCES blg_dic_category (dic_cat_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT FK_blg_post_category_blg_post_pst_id FOREIGN KEY (pst_id)
  REFERENCES blg_post (pst_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AVG_ROW_LENGTH = 1638
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_post_tag (
  pst_id int(11) NOT NULL,
  dic_tag_id int(11) NOT NULL,
  CONSTRAINT FK_blg_post_tag_blg_dic_tag_dic_tag_id FOREIGN KEY (dic_tag_id)
  REFERENCES blg_dic_tag (dic_tag_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT FK_blg_post_tag_blg_post_pst_id FOREIGN KEY (pst_id)
  REFERENCES blg_post (pst_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AVG_ROW_LENGTH = 862
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_post_user (
  usr_id int(11) NOT NULL,
  pst_id int(11) NOT NULL,
  CONSTRAINT FK_blg_post_user_blg_post_pst_id FOREIGN KEY (pst_id)
  REFERENCES blg_post (pst_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT FK_blg_post_user_blg_user_usr_id FOREIGN KEY (usr_id)
  REFERENCES blg_user (usr_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AVG_ROW_LENGTH = 1820
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_user_detail (
  usr_det_id int(11) NOT NULL AUTO_INCREMENT,
  usr_det_adres varchar(100) DEFAULT NULL,
  usr_det_birthdate date DEFAULT NULL,
  usr_det_city int(11) DEFAULT NULL,
  usr_det_country int(11) DEFAULT NULL,
  usr_det_firstname varchar(20) NOT NULL,
  usr_det_lastname varchar(20) NOT NULL,
  usr_id int(11) NOT NULL,
  usr_photo_link varchar(255) DEFAULT NULL,
  usr_gender varchar(7) DEFAULT NULL,
  PRIMARY KEY (usr_det_id),
  UNIQUE INDEX unique_usr_det_id (usr_det_id),
  UNIQUE INDEX usr_id (usr_id),
  CONSTRAINT FK_blg_user_detail_blg_user_usr_id FOREIGN KEY (usr_id)
  REFERENCES blg_user (usr_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
AUTO_INCREMENT = 39
AVG_ROW_LENGTH = 2730
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_user_facebook (
  usr_fb_id bigint(20) NOT NULL,
  usr_id int(11) NOT NULL,
  usr_fb_verified tinyint(1) NOT NULL DEFAULT 1,
  usr_fb_accesstoken varchar(255) DEFAULT NULL,
  PRIMARY KEY (usr_fb_id),
  CONSTRAINT FK_blg_user_facebook_blg_user_usr_id FOREIGN KEY (usr_id)
  REFERENCES blg_user (usr_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
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
AVG_ROW_LENGTH = 2730
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_comment_relation (
  comm_id_child int(11) NOT NULL,
  comm_id_parent int(11) NOT NULL,
  CONSTRAINT FK_blg_comment_relation_blg_comment_comm_id FOREIGN KEY (comm_id_parent)
  REFERENCES blg_comment (comm_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE blg_post_comment (
  pst_id int(11) NOT NULL,
  comm_id int(11) NOT NULL,
  CONSTRAINT FK_blg_post_comment_blg_comment_comm_id FOREIGN KEY (comm_id)
  REFERENCES blg_comment (comm_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT FK_blg_post_comment_blg_post_pst_id FOREIGN KEY (pst_id)
  REFERENCES blg_post (pst_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;