create table if not exists users (
    id bigserial primary key,
    username varchar(255) unique
);

insert into users (username) values
('Петя'),
('Ваня'),
('Саша'),
('Коля'),
('Дима'),
('Маша'),
('Лена'),
('Катя'),
('Аня');