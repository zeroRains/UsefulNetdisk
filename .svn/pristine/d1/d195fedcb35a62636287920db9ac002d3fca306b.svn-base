create table star
(
    id   varchar(255) not null,
    path text         not null
);
create table users
(
    id       varchar(255)                  not null,
    password varchar(255) default '123456' not null,
    username varchar(255)                  null,
    phone    varchar(255)                  null,
    email    varchar(255)                  null,
    info     text                          null,
    constraint users_id_uindex
        unique (id)
)
    charset = utf8;

alter table users
    add primary key (id);
insert into users (id, password, username, phone, email, info)
values  ('admin', '123456', '管理员', '18888888888', 'me@zerorains.top', '真没什么好说的'),
        ('admin123', '123456', 'admin', '18888888888', 'me@zerorians.top', '无'),
        ('Eistent', '123456', '牛人', '12828190043', 'me@zerorains.top', '无'),
        ('lulinjun_work1', '123456', '卢林军', '12312313533', 'me@zerorains.top', '不会只有我又一堆数据吧行吧'),
        ('root', '123456', 'root', '15666668888', 'me@zerorains.top', '无'),
        ('worktest', '123456', '测试工具人001', '1998821273', 'me@zerorains.top', '无'),
        ('zerorains', '123456', 'zerorains', '12345678912', 'me@zerorains.top', '无'),
        ('zerorains123', '123456', 'zerorains', '15688894456', 'me@zerorains.top', '没什么好说的');