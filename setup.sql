
-- create the users for each database
DROP USER IF EXISTS 'showcase_user'@'%';
CREATE USER 'showcase_user'@'%' IDENTIFIED BY 'showcase_password';
CREATE DATABASE IF NOT EXISTS showcase_axon_exception;
GRANT ALL PRIVILEGES ON showcase_axon_exception.* TO 'showcase_user'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON mysql.proc TO 'showcase_user'@'%' WITH GRANT OPTION;

FLUSH PRIVILEGES;