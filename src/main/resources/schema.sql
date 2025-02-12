drop table if exists myPost;
drop table if exists myMember;

create table myPost (
    id bigint generated by default as identity,
    content varchar(255),
    author varchar(255),
    title varchar(255),
    primary key (id)
);
create table myMember (
    id bigint generated by default as identity,
    member_id varchar(255),
    password varchar(255),
    primary key (id)
);

insert into myMember values (1,'id','pw');