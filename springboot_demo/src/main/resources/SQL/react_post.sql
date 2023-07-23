drop table react_post;
create table react_post(
id number primary key,
name varchar2(20) not null,
content varchar2(2000),
filename varchar2(200) default 'noimage.png',
wdate date default sysdate);

drop sequence react_post_seq;
create sequence react_post_seq nocache;