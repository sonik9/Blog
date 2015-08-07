--
-- Скрипт сгенерирован Devart dbForge Studio for MySQL, Версия 6.3.358.0
-- Домашняя страница продукта: http://www.devart.com/ru/dbforge/mysql/studio
-- Дата скрипта: 30.07.2015 17:32:40
-- Версия сервера: 5.7.7-rc-log
-- Версия клиента: 4.1
--


SET NAMES 'utf8';



INSERT INTO java_blog.blg_dic_role(role_id, role_name) VALUES
(1, 'ROLE_ADMIN');
INSERT INTO java_blog.blg_dic_role(role_id, role_name) VALUES
(2, 'ROLE_USER');

INSERT INTO java_blog.blg_dic_telephone_type(tel_type_id, tel_type_name) VALUES
(1, 'Home');
INSERT INTO java_blog.blg_dic_telephone_type(tel_type_id, tel_type_name) VALUES
(2, 'Mobile');

INSERT INTO java_blog.blg_user(usr_id, usr_dateTimeChange, usr_login, usr_password) VALUES
(1, '2015-07-03 12:54:28', 'admin', '$2a$10$GThHMT1Os9W3YbeW1buZJu9AbdPPLCOhVeSfhaXc2ob1nVc.VoDDK');
INSERT INTO java_blog.blg_user(usr_id, usr_dateTimeChange, usr_login, usr_password) VALUES
(27, '2015-07-29 18:31:49', 'sonik9@mail.ru', '$2a$10$WaFbJgcHLghxg/1gkWUCBeo/8FTzoniQmMNTY1XSGgQdrGdVp4bme');
INSERT INTO java_blog.blg_user(usr_id, usr_dateTimeChange, usr_login, usr_password) VALUES
(28, '2015-07-29 18:32:16', 'den@mail.ru', '$2a$10$PmuuZP1jImAKyfyHaUBoXeKBpv61NzKFmhdplvgQ0sKzu7ZAJ8mYe');
INSERT INTO java_blog.blg_user(usr_id, usr_dateTimeChange, usr_login, usr_password) VALUES
(29, '2015-07-30 16:33:03', 'user@mail.ru', '$2a$10$2fKYZcIEJwMLck2SsdJLouQwYLGSJm6XWI0NHN7tTdzVyIoD0Lwhu');
INSERT INTO java_blog.blg_user(usr_id, usr_dateTimeChange, usr_login, usr_password) VALUES
(32, '2015-07-30 17:28:34', 'test@test', '$2a$10$rZScVV9mCP/0DGYDXdIqXekUL47ZtnlKQNtQJdMqoHKFABH9i539W');

INSERT INTO java_blog.blg_user_detail(usr_det_id, usr_det_adres, usr_det_birthdate, usr_det_city, usr_det_country, usr_det_firstname, usr_det_lastname, usr_id) VALUES
(2, NULL, NULL, NULL, NULL, 'Admin', 'Admin', 1);
INSERT INTO java_blog.blg_user_detail(usr_det_id, usr_det_adres, usr_det_birthdate, usr_det_city, usr_det_country, usr_det_firstname, usr_det_lastname, usr_id) VALUES
(24, NULL, NULL, NULL, NULL, 'Vitalii', 'Upir', 27);
INSERT INTO java_blog.blg_user_detail(usr_det_id, usr_det_adres, usr_det_birthdate, usr_det_city, usr_det_country, usr_det_firstname, usr_det_lastname, usr_id) VALUES
(25, NULL, NULL, NULL, NULL, 'Rudnev', 'Denis', 28);
INSERT INTO java_blog.blg_user_detail(usr_det_id, usr_det_adres, usr_det_birthdate, usr_det_city, usr_det_country, usr_det_firstname, usr_det_lastname, usr_id) VALUES
(26, NULL, NULL, NULL, NULL, 'user1', 'user1', 29);
INSERT INTO java_blog.blg_user_detail(usr_det_id, usr_det_adres, usr_det_birthdate, usr_det_city, usr_det_country, usr_det_firstname, usr_det_lastname, usr_id) VALUES
(31, NULL, NULL, NULL, NULL, 'test', 'test', 32);


INSERT INTO java_blog.blg_user_role(usr_id, role_id) VALUES
(1, 1);
INSERT INTO java_blog.blg_user_role(usr_id, role_id) VALUES
(27, 2);
INSERT INTO java_blog.blg_user_role(usr_id, role_id) VALUES
(28, 2);
INSERT INTO java_blog.blg_user_role(usr_id, role_id) VALUES
(29, 2);
INSERT INTO java_blog.blg_user_role(usr_id, role_id) VALUES
(32, 2);

INSERT INTO java_blog.blg_user_telephone(usr_tel_id, usr_tel_numb, usr_id, tel_type_id) VALUES
(1, '+380636171846', 1, 1);
INSERT INTO java_blog.blg_user_telephone(usr_tel_id, usr_tel_numb, usr_id, tel_type_id) VALUES
(2, '+48790390855', 1, 2);
INSERT INTO java_blog.blg_user_telephone(usr_tel_id, usr_tel_numb, usr_id, tel_type_id) VALUES
(3, '+4800000000', 2, 2);
INSERT INTO java_blog.blg_user_telephone(usr_tel_id, usr_tel_numb, usr_id, tel_type_id) VALUES
(4, '+38111111111', 2, 1);
INSERT INTO java_blog.blg_user_telephone(usr_tel_id, usr_tel_numb, usr_id, tel_type_id) VALUES
(5, '123-333-111', 23, 1);