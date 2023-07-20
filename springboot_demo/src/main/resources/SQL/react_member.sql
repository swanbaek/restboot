drop table react_member;

create table react_member(
idx number primary key,
name varchar2(30) not null,
nickname varchar2(20) unique,
pwd varchar2(100) not null,
role varchar2(30) default 'USER',
indate date default sysdate
);

drop sequence react_member_seq;
create sequence react_member_seq nocache;