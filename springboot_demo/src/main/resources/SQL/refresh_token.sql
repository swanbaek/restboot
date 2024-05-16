drop table refresh_token;

create table refresh_token(
id number(8) primary key,
refresh_token varchar2(255) not null,
user_idx number(8) not null,
expirydate Date not null
);
drop sequence refresh_token_seq;
create sequence refresh_token_seq nocache;

/*
drop table refresh_token;
create table refresh_token(
id number(8) primary key,
refresh_token varchar2(255) not null,
user_idx number(8) not null,
expirydate number not null
);
drop sequence refresh_token_seq;
create sequence refresh_token_seq nocache;
select id,user_idx,expirydate,refresh_token from refresh_token;
select 1*24*60*60*1000 from dual;

delete from refresh_token;
commit;
select to_char(expirydate,'yy/mm/dd hh:mi:ss') from refresh_token;
select to_char(expirydate,'yy/mm/dd hh:mi:ss'),id, user_idx from refresh_token;
drop table refresh_token;

*/