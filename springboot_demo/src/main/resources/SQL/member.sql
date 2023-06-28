drop table member;
create table member(
	idx number primary key,
	name varchar2(30) not null,
	userid varchar2(20) not null unique,
	passwd varchar2(100) not null,
	role varchar2(30) default 'USER' not null,
	hp1 char(3),
	hp2 char(4),
	hp3 char(4),
	post char(5),
	addr1 varchar2(100),
	addr2 varchar2(100),
	indate date default sysdate
);

drop sequence member_seq;
create sequence member_seq nocache;


select * from member;