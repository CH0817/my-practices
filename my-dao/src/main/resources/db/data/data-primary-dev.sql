insert into user (id, email, password, create_date) VALUES ('a', 'test@email.com', '11111111', now());
insert into user (id, email, password, removed, create_date) VALUES ('b', 'test2@email.com', '11111111', 1, now());
insert into account_type (id, name, create_date, user_id) VALUES ('a', '現金', now(), 'a');
insert into account (id, name, money, create_date, user_id, account_type_id) VALUES ('a', '玉山', 1000, now(), 'a', 'a');
insert into item (id, name, create_date, user_id) VALUES ('a', '用餐', now(), 'a');
insert into trade (id, money, trade_type, trade_date, create_date, user_id, account_id, item_id)
values ('a', 10, '1', now(), now(), 'a', 'a', 'a');