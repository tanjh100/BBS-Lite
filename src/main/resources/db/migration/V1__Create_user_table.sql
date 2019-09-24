create table user
(
    id bigint auto_increment primary key not null,
    account_id varchar(20),
    email varchar(30),
    name varchar(14) default 'none',
    token varchar(36) default '',
    bio varchar(256),
    avatar_url varchar(100) default 'https://alicdn.bestsort.cn/icon/default.jpeg',
    password varchar(20) default null,
    gmt_create bigint,
    gmt_modified bigint,
    html_url varchar(50)
);