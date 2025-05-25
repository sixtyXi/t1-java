create table if not exists payments (
    id bigserial primary key,
    amount numeric(10, 2) not null,
    user_id bigint unique not null
);

insert into payments (amount, user_id)
select 0.00, gs.user_id
from generate_series(1, 100) as gs(user_id)