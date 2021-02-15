-- board 테이블 생성
create table board (
bno number not null constraint board_bno_pk primary key,
title varchar2(200) not null,
content varchar2(4000),
writer varchar2(50) not null,
regdate date default sysdate,
viewcnt number default 0
);

-- board bno 시퀀스 생성
create sequence board_seq
start with 1
increment by 1;

-- board show 컬럼 추가(삭제시 숨김)
ALTER TABLE board add show VARCHAR2(10) DEFAULT 'y' NOT NULL;


-- member 테이블 생성
create table member (
user_id varchar2(50) not null CONSTRAINT member_userid_pk primary key,
user_pw varchar2(50) not null,
user_name varchar2(50),
user_email varchar2(200) not null,
user_regdate date default sysdate,
user_updatedate date
);


-- reply 테이블 생성
create table reply (
rno number not null constraint reply_rno_pk primary key,
bno number not null,
replytext varchar2(4000),
replyer varchar2(50) not null,
regdate date default sysdate,
updatedate date
);

-- reply bno 외래키 생성 (board(bno)참조)
ALTER TABLE reply
ADD CONSTRAINT reply_bno_fk
FOREIGN KEY(bno) REFERENCES board(bno);

-- reply rno 시퀀스 생성
create sequence reply_seq
start with 1
increment by 1;


-- 첨부파일 테이블 생성
CREATE TABLE attach (
fullname VARCHAR2(150) CONSTRAINT attach_fullname_pk PRIMARY KEY,
bno NUMBER NOT NULL,
regdate DATE DEFAULT SYSDATE
);

-- attach bno 외래키 생성 (board(bno)참조)
ALTER TABLE attach
ADD CONSTRAINT attach_bno_fk
FORE IGN KEY(bno) REFERENCES board(bno);


-- product 테이블  생성
CREATE TABLE product (
product_id NUMBER CONSTRAINT product_productid_pk primary key,	-- 상품 번호
product_name VARCHAR2(50),	-- 상품 이름
product_price NUMBER DEFAULT 0,	-- 상품 가격
product_desc VARCHAR2(500),	-- 상품 상세설명
product_url VARCHAR2(500)	-- 상품 사진
);

-- product 테이블 더미 데이터 입력
INSERT INTO product VALUES (1,'나이키 데이브레이크',139000,'1979년에 출시되었던 나이키 데이브레이크가 화려했던 과거를 재현합니다. 예전과 같은 고무 와플 밑창과 나일론 갑피로 진정한 빈티지 스타일을 즐겨보세요.','nike.jpg');
INSERT INTO product VALUES (2,'아디다스 스탠스미스',109000,'1970년대 테니스 코트를 평정했던 그랜드슬램 챔피언의 이름을 따 만든 스니커즈다. 특유의 클래식함과 세련된 디자인이 돋보여 일상생활은 물론 테니스화로도 활용하기 좋은 아이템이다.','adidas.jpg');
INSERT INTO product VALUES (3,'뉴발란스 327',109000,'327은 글로벌트랜드인 70년대 조거 쉐입에 현대적인 디자인 변화를 시도한 새로운 모델로 헤리티지와 임팩트있는 미드솔이 특징입니다.','newbalance.jpg');
INSERT INTO product VALUES (4,'반스 스타일36',69000,'반스의 아이콘으로 자리잡은 재즈 스트라이프를 처음 적용한 클래식 스케이트 슈즈 디자인의 로우컷 스니커즈다.','vans.jpg');
INSERT INTO product VALUES (5,'엑셀시오르',65000,'EXCELSIOR는 "더욱 더 높이 "라는 사전적 뜻을 가진 영문 단어로, 더 높은 곳을 향하고자 하는 사람들의 희망과 열정을 상징하는 의미를 담고 있습니다.','excelsior.jpg');
INSERT INTO product VALUES (6,'우포스',55000,'OOFOS는 1970-1980년 런닝화 붐을 주도했던 미국 신발 업계의 전문가들이 프로젝트 팀을 이루어 만든 브랜드입니다.','oofos.jpg');
INSERT INTO product VALUES (7,'버켄스탁',139000,'발이 가진 본연의 기능을 보호하면서 가장 편안한 신발을 만든다는 이념 하에 브랜드 고유의 헤리티지와 장인 정신을 꾸준히 이어가고 있다.','birkenstock.jpg');
INSERT INTO product VALUES (8,'컨버스 척테일러 1970s',89000,'컨버스 스테디셀러 척 70 빈티지 캔버스 제품은 아이코닉한 디자인과 빈티지한 매력을 선보입니다 .','converse.jpg');