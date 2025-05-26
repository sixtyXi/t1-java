create table if not exists limits (
    id bigserial primary key,
    user_limit numeric(10, 2) not null,
    user_id bigint unique not null
);

insert into limits (user_limit, user_id)
select 10000.00, gs.user_id
from generate_series(1, 100) as gs(user_id)