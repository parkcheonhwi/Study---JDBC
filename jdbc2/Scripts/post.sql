
CREATE TABLE TBL_POST(
	ID NUMBER CONSTRAINT PK_POST PRIMARY KEY,
	POST_TITLE VARCHAR2(1000) NOT NULL,
	POST_CONTENT VARCHAR2(1000) NOT NULL,
	MEMBER_ID NUMBER,
	CONSTRAINT FK_POST FOREIGN KEY(MEMBER_ID)
	REFERENCES TBL_MEMBER(ID)
);

SELECT * FROM TBL_POST;

INSERT INTO SCOTT.TBL_POST
(ID, POST_TITLE, POST_CONTENT, MEMBER_ID)
VALUES(SEQ_POST.NEXTVAL, '테스트 제목1', '테스트 내용1', 2);
