create table refresh_token(
id number(8) primary key,
refresh_token varchar2(255) not null,
user_idx number(8) not null);

create sequence refresh_token_seq nocache;