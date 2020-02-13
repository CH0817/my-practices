insert into user (id, email, password, create_date) VALUES ('a', 'test@email.com', '{bcrypt}$2a$10$wq1FE6qSyJGi/vhQ4/b82uU.6n.g6b6mhLChG9Xb8K5rcKgLyeZcq', now());
insert into user (id, email, password, create_date) VALUES ('b', 'test2@email.com', '11111111', now());
insert into user (id, email, password, create_date) VALUES ('c', 'test3@email.com', '11111111', now());
insert into user (id, email, password, removed, create_date) VALUES ('d', 'test4@email.com', '11111111', 1, now());