-- 
--   Devart dbForge Studio for MySQL,  7.1.13.0
--   : http://www.devart.com/ru/dbforge/mysql/studio
--  : 7/15/2016 2:48:42 PM
--     : 5.7.11
--   : User Id=root;Host=localhost;Protocol=Ssh;Character Set=utf8;SSH Host=46.101.244.51;SSH Port=2220;SSH User=sonik9;SSH Authentication Type=Password
--     : 5.7.13
--   : User Id=root;Host=localhost;Character Set=compatibility
--     java_blog,      java_blog
-- ,          
-- 

--
--   
--
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

SET NAMES 'utf8';

USE java_blog;


--
--   "blg_comment"
--
CREATE TABLE blg_comment (
  comm_id INT(11) NOT NULL AUTO_INCREMENT,
  comm_document BLOB NOT NULL,
  comm_time_create TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  comm_enable TINYINT(1) NOT NULL DEFAULT 1,
  comm_count_like INT(11) NOT NULL DEFAULT 0,
  usr_id INT(11) NOT NULL,
  PRIMARY KEY (comm_id),
  UNIQUE INDEX UK_blg_comment_pst_comm_document (comm_document(1)),
  UNIQUE INDEX UK_blg_comment_pst_comm_id (comm_id),
  UNIQUE INDEX UK_blg_post_comment_usr_id (usr_id),
  CONSTRAINT FK_blg_comment_blg_user_usr_id FOREIGN KEY (usr_id)
    REFERENCES blg_user(usr_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_comment_relation"
--
CREATE TABLE blg_comment_relation (
  comm_id_child INT(11) NOT NULL,
  comm_id_parent INT(11) NOT NULL,
  CONSTRAINT FK_blg_comment_relation_blg_comment_comm_id FOREIGN KEY (comm_id_parent)
    REFERENCES blg_comment(comm_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_dic_category"
--
CREATE TABLE blg_dic_category (
  dic_cat_id INT(11) NOT NULL AUTO_INCREMENT,
  dic_cat_name VARCHAR(50) NOT NULL,
  dic_cat_enable TINYINT(1) NOT NULL DEFAULT 1,
  dic_cat_pstcount INT(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (dic_cat_id),
  UNIQUE INDEX pst_cat_name (dic_cat_name)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_dic_city"
--
CREATE TABLE blg_dic_city (
  city_id INT(11) NOT NULL,
  city_name VARCHAR(50) DEFAULT NULL,
  country_id INT(11) DEFAULT NULL,
  PRIMARY KEY (city_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_dic_country"
--
CREATE TABLE blg_dic_country (
  country_id INT(11) NOT NULL,
  country_name VARCHAR(50) NOT NULL,
  PRIMARY KEY (country_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_dic_role"
--
CREATE TABLE blg_dic_role (
  role_id INT(11) NOT NULL AUTO_INCREMENT,
  role_name VARCHAR(20) NOT NULL,
  PRIMARY KEY (role_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_dic_tag"
--
CREATE TABLE blg_dic_tag (
  dic_tag_id INT(11) NOT NULL AUTO_INCREMENT,
  dic_tag_name VARCHAR(50) NOT NULL,
  PRIMARY KEY (dic_tag_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_dic_telephone_type"
--
CREATE TABLE blg_dic_telephone_type (
  tel_type_id INT(11) NOT NULL AUTO_INCREMENT,
  tel_type_name VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (tel_type_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_post"
--
CREATE TABLE blg_post (
  pst_id INT(11) NOT NULL AUTO_INCREMENT,
  pst_title VARCHAR(255) NOT NULL,
  pst_document TEXT NOT NULL,
  pst_title_image VARCHAR(255) DEFAULT NULL,
  pst_time_create TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  pst_time_modify TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  pst_enable TINYINT(1) NOT NULL DEFAULT 0,
  pst_count_like INT(11) NOT NULL DEFAULT 0,
  pst_count_dislike INT(11) NOT NULL DEFAULT 0,
  pst_count_comm INT(11) NOT NULL DEFAULT 0,
  pst_url VARCHAR(255) NOT NULL,
  pst_document_short TEXT NOT NULL,
  pst_count_view INT(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (pst_id),
  INDEX UK_blg_post_pst_document (pst_document(1)),
  UNIQUE INDEX UK_blg_post_pst_id (pst_id),
  UNIQUE INDEX UK_blg_post_pst_title (pst_title)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_post_category"
--
CREATE TABLE blg_post_category (
  pst_id INT(11) DEFAULT NULL,
  dic_cat_id INT(11) DEFAULT NULL,
  CONSTRAINT FK_blg_post_category_blg_dic_category_dic_cat_id FOREIGN KEY (dic_cat_id)
    REFERENCES blg_dic_category(dic_cat_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT FK_blg_post_category_blg_post_pst_id FOREIGN KEY (pst_id)
    REFERENCES blg_post(pst_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_post_comment"
--
CREATE TABLE blg_post_comment (
  pst_id INT(11) NOT NULL,
  comm_id INT(11) NOT NULL,
  CONSTRAINT FK_blg_post_comment_blg_comment_comm_id FOREIGN KEY (comm_id)
    REFERENCES blg_comment(comm_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT FK_blg_post_comment_blg_post_pst_id FOREIGN KEY (pst_id)
    REFERENCES blg_post(pst_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_post_tag"
--
CREATE TABLE blg_post_tag (
  pst_id INT(11) NOT NULL,
  dic_tag_id INT(11) NOT NULL,
  CONSTRAINT FK_blg_post_tag_blg_dic_tag_dic_tag_id FOREIGN KEY (dic_tag_id)
    REFERENCES blg_dic_tag(dic_tag_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT FK_blg_post_tag_blg_post_pst_id FOREIGN KEY (pst_id)
    REFERENCES blg_post(pst_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_post_user"
--
CREATE TABLE blg_post_user (
  usr_id INT(11) NOT NULL,
  pst_id INT(11) NOT NULL,
  CONSTRAINT FK_blg_post_user_blg_post_pst_id FOREIGN KEY (pst_id)
    REFERENCES blg_post(pst_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT FK_blg_post_user_blg_user_usr_id FOREIGN KEY (usr_id)
    REFERENCES blg_user(usr_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_user"
--
CREATE TABLE blg_user (
  usr_id INT(11) NOT NULL AUTO_INCREMENT,
  usr_dateTimeChange TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  usr_login VARCHAR(30) NOT NULL,
  usr_password VARCHAR(100) NOT NULL,
  PRIMARY KEY (usr_id),
  UNIQUE INDEX usr_login (usr_login)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_user_detail"
--
CREATE TABLE blg_user_detail (
  usr_det_id INT(11) NOT NULL AUTO_INCREMENT,
  usr_det_adres VARCHAR(100) DEFAULT NULL,
  usr_det_birthdate DATE DEFAULT NULL,
  usr_det_city INT(11) DEFAULT NULL,
  usr_det_country INT(11) DEFAULT NULL,
  usr_det_firstname VARCHAR(20) NOT NULL,
  usr_det_lastname VARCHAR(20) NOT NULL,
  usr_id INT(11) NOT NULL,
  usr_photo_link VARCHAR(255) DEFAULT NULL,
  usr_gender VARCHAR(7) DEFAULT NULL,
  PRIMARY KEY (usr_det_id),
  UNIQUE INDEX unique_usr_det_id (usr_det_id),
  UNIQUE INDEX usr_id (usr_id),
  CONSTRAINT FK_blg_user_detail_blg_user_usr_id FOREIGN KEY (usr_id)
    REFERENCES blg_user(usr_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_user_facebook"
--
CREATE TABLE blg_user_facebook (
  usr_fb_id BIGINT(20) NOT NULL,
  usr_id INT(11) NOT NULL,
  usr_fb_verified TINYINT(1) NOT NULL DEFAULT 1,
  usr_fb_accesstoken VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (usr_fb_id),
  CONSTRAINT FK_blg_user_facebook_blg_user_usr_id FOREIGN KEY (usr_id)
    REFERENCES blg_user(usr_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_user_mail"
--
CREATE TABLE blg_user_mail (
  usr_mail_id INT(11) NOT NULL,
  usr_id INT(11) NOT NULL,
  usr_mail VARCHAR(50) NOT NULL,
  usr_mail_domain VARCHAR(20) NOT NULL,
  PRIMARY KEY (usr_mail_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_user_role"
--
CREATE TABLE blg_user_role (
  usr_id INT(11) NOT NULL,
  role_id INT(11) NOT NULL,
  CONSTRAINT FK_blg_user_role_blg_dic_role_role_id FOREIGN KEY (role_id)
    REFERENCES blg_dic_role(role_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT FK_blg_user_role_blg_user_usr_id FOREIGN KEY (usr_id)
    REFERENCES blg_user(usr_id) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

--
--   "blg_user_telephone"
--
CREATE TABLE blg_user_telephone (
  usr_tel_id INT(11) NOT NULL AUTO_INCREMENT,
  usr_tel_numb VARCHAR(20) NOT NULL,
  usr_id INT(11) NOT NULL,
  tel_type_id INT(11) NOT NULL,
  PRIMARY KEY (usr_tel_id)
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci
ROW_FORMAT = DYNAMIC;

--
--   
--
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;