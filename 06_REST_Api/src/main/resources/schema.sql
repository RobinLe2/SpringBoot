USE db_boot06;

DROP TABLE IF EXISTS tbl_user;
CREATE TABLE IF NOT EXISTS tbl_user (
  uid        INT NOT NULL AUTO_INCREMENT,
  username   VARCHAR(100) NOT NULL UNIQUE,
  password   VARCHAR(100) NOT NULL,
  nickname   VARCHAR(100) NOT NULL,
  created_at DATETIME,
  CONSTRAINT pk_user PRIMARY KEY(uid)
) Engine=InnoDB;