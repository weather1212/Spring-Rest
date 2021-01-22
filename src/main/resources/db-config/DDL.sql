-- board 테이블 생성
create table board (
bno number not null constraints board_bno_pk primary key,
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
