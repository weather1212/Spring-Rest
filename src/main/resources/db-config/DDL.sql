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

-- reply rno 시퀀스 생성
create sequence reply_seq
start with 1
increment by 1;