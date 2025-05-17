create table if not exists products
(
    id bigserial primary key,
    account_number uuid unique not null,
    balance numeric(10, 2) not null,
    type varchar(20) not null,
    user_id bigint references users (id)
);

insert into products (account_number, balance, type, user_id)
values ('702f5a7e-03fe-4fff-bf36-5734f10c466a', 0.00, 'ACCOUNT', 1),
       ('67fe23fd-a43f-430b-9fda-bb94e8635ed3', 2000.00, 'CARD', 1),
       ('b2945f0d-bd03-453e-a088-87cbaca911da', 1500000.00, 'ACCOUNT', 3),
       ('61f3655c-335e-4ff2-9b0d-ddc044ebef1d', 3000.00, 'CARD', 4),
       ('d975c766-ad3f-4df5-8b86-fa31cd295461', 2500.00, 'ACCOUNT', 5),
       ('54500d69-d756-4344-b687-dcc5fbca1640', 350.00, 'CARD', 7),
       ('037c865d-1e7f-482b-bfc9-50a426d1a50f', 4000.00, 'ACCOUNT', 7),
       ('7a4ea498-ae2f-4fe8-b1cd-9cd9f5429f17', 45000.00, 'CARD', 8);