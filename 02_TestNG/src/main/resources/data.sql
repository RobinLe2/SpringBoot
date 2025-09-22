USE db_boot02;

INSERT INTO tbl_board(title, content, created_at)
VALUES ('테스트 제목', '테스트 내용', NOW());

COMMIT;