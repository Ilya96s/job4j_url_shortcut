CREATE TABLE IF NOT EXISTS url
(
    id       SERIAL PRIMARY KEY,
    longUrl  VARCHAR NOT NULL UNIQUE,
    shortUrl VARCHAR NOT NULL UNIQUE,
    count    INT     NOT NULL,
    site_id  INT     NOT NULL REFERENCES site (id)
);

comment on table url is 'Таблица ссылок';
comment on column url.id is 'Идентификатор ссылки';
comment on column url.longUrl is 'Ссылка полученная от клиента';
comment on column url.shortUrl is 'Сокращенный вариант ссылки, полученной от клиента';
comment on column url.count is 'Счетчик, показывающий количество вызовов сокращенной ссылки';
comment on column url.site_id is 'Идентификатор сайта';