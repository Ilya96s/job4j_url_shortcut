CREATE TABLE IF NOT EXISTS site
(
    id       SERIAL PRIMARY KEY,
    domain   VARCHAR NOT NULL UNIQUE,
    login    VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

comment on table site is 'Таблица сайтов';
comment on column site.id is 'Идентификатор сайта';
comment on column site.domain is 'Домен';
comment on column site.login is 'Логин, сгенерированный на стороне сервера';
comment on column site.password is 'Пароль, сгенерированный на стороне сервера';