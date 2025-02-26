DROP DATABASE IF EXISTS jt_hibernate_jpa_bookdb2;
DROP USER IF EXISTS `bookadmin2`@`%`;
DROP USER IF EXISTS `bookuser2`@`%`;
CREATE DATABASE IF NOT EXISTS jt_hibernate_jpa_bookdb2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `bookadmin2`@`%` IDENTIFIED WITH mysql_native_password BY 'bookadmin2';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
    CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `jt_hibernate_jpa_bookdb2`.* TO `bookadmin2`@`%`;
CREATE USER IF NOT EXISTS `bookuser2`@`%` IDENTIFIED WITH mysql_native_password BY 'bookuser2';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `jt_hibernate_jpa_bookdb2`.* TO `bookuser2`@`%`;
FLUSH PRIVILEGES;