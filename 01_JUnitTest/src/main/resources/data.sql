USE db_boot01;

INSERT INTO tbl_board(title, content, created_at)
VALUES ('테스트 제목', '테스트 내용', NOW());

COMMIT;