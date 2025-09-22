USE db_boot03;

DROP TABLE IF EXISTS tbl_board;
CREATE TABLE IF NOT EXISTS tbl_board (
  bid        INT NOT NULL AUTO_INCREMENT,
  title      VARCHAR(100) NOT NULL,
  content    VARCHAR(100),
  created_at DATETIME,
  CONSTRAINT pk_board PRIMARY KEY(bid)
) Engine=InnoDB;